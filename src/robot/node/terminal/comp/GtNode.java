package robot.node.terminal.comp;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramIntNode;

public class GtNode implements RobotProgramCondNode {

	final RobotProgramIntNode expr1;
	final RobotProgramIntNode expr2;

	public GtNode(RobotProgramIntNode expr1, RobotProgramIntNode expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return expr1.evaluate(robot) > expr2.evaluate(robot);
	}

	@Override
	public String toString() {
		return expr1 + " > " + expr2;
	}

}
