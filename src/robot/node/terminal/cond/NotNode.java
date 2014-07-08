package robot.node.terminal.cond;

import robot.Robot;
import robot.node.RobotProgramCondNode;

public class NotNode implements RobotProgramCondNode {

	final RobotProgramCondNode cond;

	public NotNode(RobotProgramCondNode cond) {
		this.cond = cond;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return !(cond.evaluate(robot));
	}

	@Override
	public String toString() {
		return "!(" + cond + ")";
	}

}
