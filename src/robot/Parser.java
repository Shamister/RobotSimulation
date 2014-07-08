package robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import robot.node.ParenCondNode;
import robot.node.ParenIntNode;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramIntNode;
import robot.node.RobotProgramNode;
import robot.node.nonterminal.ACTNode;
import robot.node.nonterminal.ASSGNNode;
import robot.node.nonterminal.BLOCKNode;
import robot.node.nonterminal.BOOLNode;
import robot.node.nonterminal.CONDNode;
import robot.node.nonterminal.DONode;
import robot.node.nonterminal.IFNode;
import robot.node.nonterminal.LOOPNode;
import robot.node.nonterminal.NUMNode;
import robot.node.nonterminal.PROGNode;
import robot.node.nonterminal.STMTNode;
import robot.node.nonterminal.VARNode;
import robot.node.nonterminal.WHILENode;
import robot.node.terminal.act.MoveNode;
import robot.node.terminal.act.ShieldOnNode;
import robot.node.terminal.act.TakeFuelNode;
import robot.node.terminal.act.TurnAroundNode;
import robot.node.terminal.act.TurnLNode;
import robot.node.terminal.act.TurnRNode;
import robot.node.terminal.act.WaitNode;
import robot.node.terminal.comp.EqNode;
import robot.node.terminal.comp.GtEqNode;
import robot.node.terminal.comp.GtNode;
import robot.node.terminal.comp.LtEqNode;
import robot.node.terminal.comp.LtNode;
import robot.node.terminal.comp.NotEqNode;
import robot.node.terminal.cond.NotNode;
import robot.node.terminal.ifelse.ElseNode;
import robot.node.terminal.ifelse.IfNode;
import robot.node.terminal.logic.AndNode;
import robot.node.terminal.logic.OrNode;
import robot.node.terminal.logic.XorNode;
import robot.node.terminal.op.AddNode;
import robot.node.terminal.op.DivNode;
import robot.node.terminal.op.MulNode;
import robot.node.terminal.op.SubNode;
import robot.node.terminal.sensor.BarrelFBNode;
import robot.node.terminal.sensor.BarrelLRNode;
import robot.node.terminal.sensor.FuelLeftNode;
import robot.node.terminal.sensor.NumBarrelsNode;
import robot.node.terminal.sensor.OppFBNode;
import robot.node.terminal.sensor.OppLRNode;
import robot.node.terminal.sensor.WallDistNode;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */

public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement
														// this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	private static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	private static Pattern VARNAME = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");
	private static Pattern LOGIC = Pattern.compile("((\\&\\&)|(\\|\\|)|(\\^))");
	private static Pattern COMP = Pattern
			.compile("(\\>\\=|\\<\\=|\\=\\=|\\!\\=|\\>|\\<)");
	private static Pattern OP = Pattern.compile("(\\+|\\-|\\/|\\*)");
	private static Pattern OPENPAREN = Pattern.compile("\\(");
	private static Pattern CLOSEPAREN = Pattern.compile("\\)");
	private static Pattern OPENBRACE = Pattern.compile("\\{");
	private static Pattern CLOSEBRACE = Pattern.compile("\\}");

	/**
	 * PROG ::= STMT+
	 */

	static RobotProgramNode parseProgram(Scanner s) {
		List<RobotProgramNode> children = new ArrayList<RobotProgramNode>();
		// THE PARSER GOES HERE!

		BLOCK block = new BLOCK();

		while (s.hasNext()) {
			children.add(parseSTMT(s, block));
		}
		return new PROGNode(children);
	}

	static RobotProgramNode parseSTMT(Scanner s, BLOCK block) {
		RobotProgramNode node = null;
		if (s.hasNext()) {
			if (s.hasNext("move") || s.hasNext("turnL") || s.hasNext("turnR")
					|| s.hasNext("turnAround") || s.hasNext("shieldOn")
					|| s.hasNext("shieldOff") || s.hasNext("takeFuel")
					|| s.hasNext("wait")) {
				node = parseACT(s, block);
			} else if (s.hasNext("loop")) {
				node = parseLOOP(s, block);
			} else if (s.hasNext("if")) {
				node = parseIF(s, block);
			} else if (s.hasNext("while")) {
				node = parseWHILE(s, block);
			} else if (s.hasNext("var")) {
				node = parseDCLR(s, block);
			} else if (s.hasNext(VARNAME)) {
				node = parseASSGN(s, block);
			} else if (s.hasNext(OPENBRACE)) {
				node = parseBLOCK(s, block);
			} else if (s.hasNext("do")) {
				node = parseDO(s, block);
			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}
		}
		if (node == null)
			return null;
		return new STMTNode(node);
	}

	static RobotProgramNode parseDO(Scanner s, BLOCK block) {
		if (s.hasNext("do")) {
			s.next("do");

			RobotProgramNode stmt = parseSTMT(s, block);

			if (s.hasNext("while")) {
				s.next("while");

				if (stmt != null) {
					if (s.hasNext(OPENPAREN)) {
						s.next(OPENPAREN);
					} else {
						fail("Syntax error, insert '(' before conditonal statement",
								s);
					}
					RobotProgramCondNode cond = parseCOND(null, null, s, block);
					if (s.hasNext(CLOSEPAREN)) {
						s.next(CLOSEPAREN);
					} else {
						fail("Syntax error, insert ')' after conditonal statement",
								s);
					}

					if (stmt != null && cond != null) {
						return new DONode(stmt, cond);
					}
				}
			} else {
				fail("Syntax Error : 'do' syntax without 'while'", s);
			}
		} else {
			fail("Unexpected syntax : " + s.next(), s);
		}

		return null;
	}

	static RobotProgramNode parseASSGN(Scanner s, BLOCK block) {
		if (s.hasNext(VARNAME)) {
			String token = s.next(VARNAME);

			if (s.hasNext("=")) {
				s.next("=");

				RobotProgramIntNode expr = parseEXP(s, block);
				removeEndChar(s);

				if (expr != null
						&& (block.containsVarThisBlock(token) || block
								.containsVarOuterBlock(token))) {
					// get the variable either in this block or outer block
					RobotProgramIntNode var = block.getVarThisBlock(token);
					if (var == null)
						var = block.getVarOuterBlock(token);

					return new ASSGNNode(token, var, expr);
				} else {
					fail("Syntax error, variable has not been declared yet", s);
				}
			}
		} else {
			fail("Syntax error, variable does not have a name", s);
		}
		return null;
	}

	static RobotProgramNode parseDCLR(Scanner s, BLOCK block) {
		if (s.hasNext("var")) {
			s.next("var");

			if (s.hasNext(VARNAME)) {
				String token = s.next(VARNAME);

				if (s.hasNext("=")) {
					s.next("=");

					RobotProgramIntNode expr = parseEXP(s, block);
					removeEndChar(s);

					if (expr != null && !block.containsVarThisBlock(token)) {
						RobotProgramIntNode var = new VARNode(token, expr);
						block.addVar(token, var);
						return new ASSGNNode(token, var, expr);
					} else {
						fail("Syntax error, variable has been declared already in this block",
								s);
					}
				}
			} else {
				fail("Syntax error, variable does not have a name", s);
			}
		}
		return null;
	}

	static RobotProgramNode parseACT(Scanner s, BLOCK block) {
		RobotProgramNode node = null;
		if (s.hasNext()) {
			if (s.hasNext("move") || s.hasNext("wait")) {
				String token = null;

				if (s.hasNext("move")) {
					token = s.next("move");
				} else if (s.hasNext("wait")) {
					token = s.next("wait");
				}

				if (token != null) {
					RobotProgramIntNode value = null;

					if (s.hasNext(OPENPAREN)) {
						s.next(OPENPAREN);

						value = parseEXP(s, block);
						if (s.hasNext(CLOSEPAREN)) {
							s.next(CLOSEPAREN);
						} else {
							fail("Syntax error, insert ')' after conditonal statement",
									s);
						}
					}

					if (token.equals("move")) {
						if (value != null) {
							node = new MoveNode(value);
						} else {
							node = new MoveNode();
						}
					} else if (token.equals("wait")) {
						if (value != null) {
							node = new WaitNode(value);
						} else {
							node = new WaitNode();
						}
					}
				}

			} else if (s.hasNext("turnL")) {
				s.next("turnL");
				node = new TurnLNode();
			} else if (s.hasNext("turnR")) {
				s.next("turnR");
				node = new TurnRNode();
			} else if (s.hasNext("turnAround")) {
				s.next("turnAround");
				node = new TurnAroundNode();
			} else if (s.hasNext("shieldOn")) {
				s.next("shieldOn");

				RobotProgramCondNode cond = null;

				if (s.hasNext(OPENPAREN)) {
					s.next(OPENPAREN);

					cond = parseCOND(null, null, s, block);

					if (s.hasNext(CLOSEPAREN)) {
						s.next(CLOSEPAREN);
					} else {
						fail("Syntax error, insert ')' after conditonal statement",
								s);
					}
				} else {
					fail("Unexpected syntax : " + s.next(), s);
				}
				if (cond != null)
					node = new ShieldOnNode(cond);
				else {
					node = new ShieldOnNode();
				}
			} else if (s.hasNext("takeFuel")) {
				s.next("takeFuel");
				node = new TakeFuelNode();
			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}
		}
		removeEndChar(s);
		if (node == null)
			return null;
		return new ACTNode(node);
	}

	static RobotProgramNode parseLOOP(Scanner s, BLOCK block) {
		RobotProgramNode stmt = null;
		if (s.hasNext("loop")) {
			s.next("loop");
			stmt = parseSTMT(s, block);

		} else {
			fail("Unexpected syntax : " + s.next(), s);
		}
		if (stmt == null)
			return null;
		return new LOOPNode(stmt);
	}

	static RobotProgramNode parseIF(Scanner s, BLOCK block) {
		if (s.hasNext("if")) {
			RobotProgramCondNode ifTerminal = null;
			RobotProgramCondNode elseTerminal = null;

			List<RobotProgramCondNode> elifTerminalList = new ArrayList<RobotProgramCondNode>();

			s.next("if");
			if (s.hasNext(OPENPAREN)) {
				s.next(OPENPAREN);
			} else {
				fail("Syntax error, insert '(' before conditonal statement", s);
			}

			RobotProgramCondNode condIf = parseCOND(null, null, s, block);

			if (s.hasNext(CLOSEPAREN)) {
				s.next(CLOSEPAREN);
			} else {
				fail("Syntax error, insert ')' after conditonal statement", s);
			}

			RobotProgramNode stmtIf = parseSTMT(s, block);

			if (condIf != null && stmtIf != null) {
				ifTerminal = new IfNode(condIf, stmtIf);
			}

			// check whether it has elif block
			while (s.hasNext("elif")) {
				s.next("elif");

				if (s.hasNext(OPENPAREN)) {
					s.next(OPENPAREN);
				} else {
					fail("Syntax error, insert '(' before conditonal statement",
							s);
				}
				RobotProgramCondNode condElif = parseCOND(null, null, s, block);
				if (s.hasNext(CLOSEPAREN)) {
					s.next(CLOSEPAREN);
				} else {
					fail("Syntax error, insert ')' after conditonal statement",
							s);
				}
				RobotProgramNode stmtElif = parseBLOCK(s, block);

				if (condElif != null && stmtElif != null) {
					elifTerminalList.add(new IfNode(condElif, stmtElif));
				}
			}

			// check whether it has else block
			if (s.hasNext("else")) {
				s.next("else");

				RobotProgramNode stmtElse = parseBLOCK(s, block);

				if (stmtElse != null) {
					elseTerminal = new ElseNode(stmtElse);
				}
			}

			if (ifTerminal != null && elifTerminalList.isEmpty())
				return new IFNode(ifTerminal, elseTerminal);

			if (ifTerminal != null) {
				return new IFNode(ifTerminal, elifTerminalList, elseTerminal);
			}
		}

		return null;
	}

	static RobotProgramNode parseWHILE(Scanner s, BLOCK block) {
		if (s.hasNext("while")) {
			s.next("while");
			if (s.hasNext(OPENPAREN)) {
				s.next(OPENPAREN);
			} else {
				fail("Syntax error, insert '(' before conditonal statement", s);
			}
			RobotProgramCondNode cond = parseCOND(null, null, s, block);
			if (s.hasNext(CLOSEPAREN)) {
				s.next(CLOSEPAREN);
			} else {
				fail("Syntax error, insert ')' after conditonal statement", s);
			}
			RobotProgramNode stmt = parseSTMT(s, block);

			if (cond != null && stmt != null) {
				return new WHILENode(cond, stmt);
			}
		} else {
			fail("Unexpected syntax : " + s.next(), s);
		}
		return null;
	}

	static RobotProgramCondNode parseSingleCond(Scanner s, BLOCK block) {
		if (s.hasNext("true")) {
			s.next("true");
			return new BOOLNode(true);
		} else if (s.hasNext("false")) {
			s.next("false");
			return new BOOLNode(false);
		} else if (s.hasNext("\\!")) {
			s.next("\\!");
			return new NotNode(parseSingleCond(s, block));
		} else if (s.hasNext(VARNAME) || s.hasNext(NUMPAT)) {
			return parseCOMP(s, block);
		} else if (s.hasNext(OPENPAREN)) {
			s.next(OPENPAREN);

			RobotProgramCondNode cond = parseCOND(null, null, s, block);

			if (s.hasNext(CLOSEPAREN)) {
				s.next(CLOSEPAREN);
				return new ParenCondNode(cond);
			} else {
				fail("Syntax error : insert ')' to close the parenthesis", s);
			}
		} else {
			fail("Unexpected syntax : " + s.next(), s);
		}
		return null;
	}

	static RobotProgramCondNode parseCOMP(Scanner s, BLOCK block) {
		if (s.hasNext()) {
			RobotProgramIntNode value = parseEXP(s, block);

			String token = null;
			if (s.hasNext(COMP)) {
				if (s.hasNext("\\>")) {
					token = s.next("\\>");
				} else if (s.hasNext("\\<")) {
					token = s.next("\\<");
				} else if (s.hasNext("\\=\\=")) {
					token = s.next("\\=\\=");
				} else if (s.hasNext("\\>\\=")) {
					token = s.next("\\>\\=");
				} else if (s.hasNext("\\<\\=")) {
					token = s.next("\\<\\=");
				} else if (s.hasNext("\\!\\=")) {
					token = s.next("\\!\\=");
				}
			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}

			RobotProgramIntNode value2 = parseEXP(s, block);

			if (s.hasNext(COMP)) {
				fail("Unexpected comparator : " + s.next(), s);
			}

			if (value != null && value2 != null) {
				if (token.equals(">")) {
					return new GtNode(value, value2);
				} else if (token.equals("<")) {
					return new LtNode(value, value2);
				} else if (token.equals("==")) {
					return new EqNode(value, value2);
				} else if (token.equals(">=")) {
					return new GtEqNode(value, value2);
				} else if (token.equals("<=")) {
					return new LtEqNode(value, value2);
				} else if (token.equals("!=")) {
					return new NotEqNode(value, value2);
				}
			}

		} else {
			fail("Unexpected syntax : " + s.next(), s);
		}
		return null;
	}

	static RobotProgramCondNode parseCOND(RobotProgramCondNode cond, String op,
			Scanner s, BLOCK block) {
		if (s.hasNext()) {
			RobotProgramCondNode cond2 = parseSingleCond(s, block);

			if (cond != null && op != null) {
				if (op.equals("&&")) {
					cond2 = new AndNode(cond, cond2);
				} else if (op.equals("||")) {
					cond2 = new OrNode(cond, cond2);
				} else if (op.equals("^")) {
					cond2 = new XorNode(cond, cond2);
				}
			}

			if (s.hasNext(LOGIC)) {
				String token = null;
				if (s.hasNext("\\&\\&")) {
					token = s.next("\\&\\&");
				} else if (s.hasNext("\\|\\|")) {
					token = s.next("\\|\\|");
				} else if (s.hasNext("\\^")) {
					token = s.next("\\^");
				}
				return parseCOND(cond2, token, s, block);
			} else {
				return new CONDNode(cond2);
			}

		} else {
			fail("Conditional statement is missing or incomplete", s);
		}
		return null;
	}

	static RobotProgramIntNode parseEXP(Scanner s, BLOCK block) {
		RobotProgramIntNode value = null;
		if (s.hasNext()) {
			if (s.hasNext(NUMPAT)) {
				value = parseNUM(s);
				if (s.hasNext(OP)) {
					value = parseOP(value, s, block);
				}
			} else if (s.hasNext("fuelLeft") || s.hasNext("oppLR")
					|| s.hasNext("oppFB") || s.hasNext("numBarrels")
					|| s.hasNext("barrelLR") || s.hasNext("barrelFB")
					|| s.hasNext("wallDist")) {
				value = parseSEN(s, block);
			} else if (s.hasNext(VARNAME)) {
				value = parseVAR(s, block);

				if (s.hasNext(OP)) {
					value = parseOP(value, s, block);
				}
			} else if (s.hasNext(OPENPAREN)) {
				s.next(OPENPAREN);
				value = new ParenIntNode(parseEXP(s, block));
				if (s.hasNext(CLOSEPAREN)) {
					s.next(CLOSEPAREN);
				} else {
					fail("Syntax error : insert ')' to close the parenthesis",
							s);
				}
				if (s.hasNext(OP)) {
					value = parseOP(value, s, block);
				}
			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}
		}

		if (value == null)
			return null;
		return value;
	}

	static RobotProgramIntNode parseVAR(Scanner s, BLOCK block) {
		if (s.hasNext()) {
			if (s.hasNext(VARNAME)) {
				String token = s.next(VARNAME);

				if (block.containsVarThisBlock(token)) {
					return block.getVarThisBlock(token);
				} else if (block.containsVarOuterBlock(token)) {
					return block.getVarOuterBlock(token);
				} else {
					fail("Undeclared variable : " + token, s);
				}
			}
		}
		return null;
	}

	static RobotProgramIntNode parseOP(RobotProgramIntNode value, Scanner s,
			BLOCK block) {
		RobotProgramIntNode expr = null;
		if (s.hasNext()) {
			String token = null;

			if (s.hasNext("\\*") || s.hasNext("\\/")) {

				if (s.hasNext("\\*")) {
					token = s.next("\\*");
				} else if (s.hasNext("\\/")) {
					token = s.next("\\/");
				}

				if (token != null) {

					if (s.hasNext(VARNAME)) {
						expr = block.getVarThisBlock(s.next(VARNAME));
						if (expr == null)
							expr = block.getVarOuterBlock(s.next(VARNAME));
					} else if (s.hasNext(NUMPAT)) {
						expr = parseNUM(s);
					} else if (s.hasNext("fuelLeft") || s.hasNext("oppLR")
							|| s.hasNext("oppFB") || s.hasNext("numBarrels")
							|| s.hasNext("barrelLR") || s.hasNext("barrelFB")
							|| s.hasNext("wallDist")) {
						expr = parseSEN(s, block);
					} else if (s.hasNext(OPENPAREN)) {
						s.next(OPENPAREN);
						expr = new ParenIntNode(parseEXP(s, block));
						if (s.hasNext(CLOSEPAREN)) {
							s.next(CLOSEPAREN);
						} else {
							fail("Syntax error : insert ')' to close the parenthesis",
									s);
						}
					}

					if (expr != null) {
						if (token.equals("*")) {
							expr = new MulNode(value, expr);
						} else if (token.equals("/")) {
							expr = new DivNode(value, expr);
						}
					}

					if (s.hasNext(OP)) {
						return parseOP(expr, s, block);
					} else {
						return expr;
					}

				}
			} else if (s.hasNext("\\+") || s.hasNext("\\-")) {

				if (s.hasNext("\\+")) {
					token = s.next("\\+");
				} else if (s.hasNext("\\-")) {
					token = s.next("\\-");
				}

				expr = parseEXP(s, block);

				if (expr != null) {
					if (token.equals("+")) {
						return new AddNode(value, expr);
					} else if (token.equals("-")) {
						return new SubNode(value, expr);
					}
				}

			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}
		}

		return null;
	}

	static RobotProgramIntNode parseSEN(Scanner s, BLOCK block) {
		RobotProgramIntNode node = null;
		if (s.hasNext()) {
			if (s.hasNext("fuelLeft")) {
				s.next("fuelLeft");
				node = new FuelLeftNode();
			} else if (s.hasNext("oppLR")) {
				s.next("oppLR");
				node = new OppLRNode();
			} else if (s.hasNext("oppFB")) {
				s.next("oppFB");
				node = new OppFBNode();
			} else if (s.hasNext("numBarrels")) {
				s.next("numBarrels");
				node = new NumBarrelsNode();
			} else if (s.hasNext("barrelLR") || s.hasNext("barrelFB")) {
				String token = null;

				if (s.hasNext("barrelLR")) {
					token = s.next("barrelLR");
				} else if (s.hasNext("barrelFB")) {
					token = s.next("barrelFB");
				}

				if (token != null) {
					RobotProgramIntNode value = null;

					if (s.hasNext(OPENPAREN)) {
						s.next(OPENPAREN);

						value = parseEXP(s, block);
						if (s.hasNext(CLOSEPAREN)) {
							s.next(CLOSEPAREN);
						} else {
							fail("Syntax error, insert ')' after conditonal statement",
									s);
						}
					}

					if (token.equals("barrelLR")) {
						if (value != null) {
							node = new BarrelLRNode(value);
						} else {
							node = new BarrelLRNode();
						}
					} else if (token.equals("barrelFB")) {
						if (value != null) {
							node = new BarrelFBNode(value);
						} else {
							node = new BarrelFBNode();
						}
					}
				}
			} else if (s.hasNext("wallDist")) {
				s.next("wallDist");
				node = new WallDistNode();
			} else {
				fail("Unexpected syntax : " + s.next(), s);
			}
		}
		if (node == null)
			return null;
		return node;
	}

	static RobotProgramNode parseBLOCK(Scanner s, BLOCK block) {
		List<RobotProgramNode> children = new ArrayList<RobotProgramNode>();

		BLOCK innerBlock = new BLOCK();
		block.setInnerBlock(innerBlock);
		innerBlock.setOuterBlock(block);

		if (s.hasNext(OPENBRACE)) {
			s.next(OPENBRACE);

			while (s.hasNext() && !s.hasNext(CLOSEBRACE)) {
				children.add(parseSTMT(s, innerBlock));
			}

			if (s.hasNext(CLOSEBRACE)) {
				s.next(CLOSEBRACE);
			} else {
				fail("Syntax error, insert '}' to close the loop block", s);
			}
		} else {
			fail("Syntax error, insert '{}' for loop block", s);
		}
		return new BLOCKNode(children);
	}

	static RobotProgramIntNode parseNUM(Scanner s) {
		RobotProgramIntNode value = null;
		if (s.hasNext(NUMPAT)) {
			int number = Integer.parseInt(s.next(NUMPAT));
			value = new NUMNode(number);
		} else {
			fail("Syntax error, it is not a number or the number does not exist",
					s);
		}
		if (value == null)
			return null;
		return value;
	}

	static void removeEndChar(Scanner s) {
		if (s.hasNext(";")) {
			s.next(";");
		} else {
			fail("Syntax error, insert ';' to complete block statements", s);
		}
	}

	// utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * If the next token in the scanner matches the specified pattern, consume
	 * the token and return true. Otherwise return false without consuming
	 * anything. Useful for dealing with the syntactic elements of the language
	 * which do not have semantic content, and are there only to make the
	 * language parsable.
	 */
	static boolean gobble(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean gobble(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public
// (or private)
