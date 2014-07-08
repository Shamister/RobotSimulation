package robot.node.terminal.op;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class SubNode implements RobotProgramIntNode {

	RobotProgramIntNode expr1;
	RobotProgramIntNode expr2;

	public SubNode(RobotProgramIntNode expr1, RobotProgramIntNode expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public int evaluate(Robot robot) {
		return expr1.evaluate(robot) - expr2.evaluate(robot);
	}

	@Override
	public String toString() {
		return expr1 + " - " + expr2;
	}

}
