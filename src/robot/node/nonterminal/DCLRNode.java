package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramIntNode;
import robot.node.RobotProgramNode;

public class DCLRNode implements RobotProgramNode {

	final String name;
	RobotProgramIntNode var;
	RobotProgramIntNode expr;

	public DCLRNode(String name, RobotProgramIntNode var) {
		this.name = name;
		this.var = var;
		this.expr = null;
	}

	public DCLRNode(String name, RobotProgramIntNode var,
			RobotProgramIntNode expr) {
		this.name = name;
		this.var = var;
		this.expr = expr;
	}

	@Override
	public void execute(Robot robot) {
		if (expr != null) {
			VARNode variable = (VARNode) var;
			variable.setValue(new NUMNode(expr.evaluate(robot)));
		}
	}

	@Override
	public String toString() {
		if (expr != null) {
			return name + " = " + expr + ";";
		}
		return name + "= " + var + ";";
	}

}
