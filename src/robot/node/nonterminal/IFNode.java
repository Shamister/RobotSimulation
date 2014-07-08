package robot.node.nonterminal;

import java.util.List;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class IFNode implements RobotProgramNode {

	final RobotProgramCondNode condIf;
	final List<RobotProgramCondNode> condElif;
	final RobotProgramCondNode condElse;

	public IFNode(RobotProgramCondNode condIf) {
		this.condIf = condIf;
		this.condElif = null;
		this.condElse = null;
	}

	public IFNode(RobotProgramCondNode condIf, RobotProgramCondNode condElse) {
		this.condIf = condIf;
		this.condElif = null;
		this.condElse = condElse;
	}

	public IFNode(RobotProgramCondNode condIf,
			List<RobotProgramCondNode> condElif, RobotProgramCondNode condElse) {
		this.condIf = condIf;
		this.condElif = condElif;
		this.condElse = condElse;
	}

	@Override
	public void execute(Robot robot) {
		if (!condIf.evaluate(robot)) {
			if (condElif != null) {
				int i = 0;
				while (i < condElif.size()) {
					if (condElif.get(i).evaluate(robot)) {
						return;
					}
					i++;
				}
			}
			if (condElse != null) {
				condElse.evaluate(robot);
			}
		}
	}

	@Override
	public String toString() {
		String msg = condIf.toString();

		if (condElif != null) {
			for (RobotProgramCondNode elif : condElif) {
				msg += " el" + elif;
			}
		}

		if (condElse != null) {
			return msg + " " + condElse;
		}
		return msg;
	}

}
