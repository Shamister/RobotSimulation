package robot.node.terminal.logic;

import robot.Robot;
import robot.node.RobotProgramCondNode;

public class OrNode implements RobotProgramCondNode {

	final RobotProgramCondNode cond1;
	final RobotProgramCondNode cond2;

	public OrNode(RobotProgramCondNode cond1, RobotProgramCondNode cond2) {
		this.cond1 = cond1;
		this.cond2 = cond2;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return (cond1.evaluate(robot) || cond2.evaluate(robot));
	}

	@Override
	public String toString() {
		return cond1 + " || " + cond2;
	}

}
