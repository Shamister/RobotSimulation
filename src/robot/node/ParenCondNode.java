package robot.node;

import robot.Robot;

public class ParenCondNode implements RobotProgramCondNode {

	final RobotProgramCondNode child;

	public ParenCondNode(RobotProgramCondNode child) {
		this.child = child;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return child.evaluate(robot);
	}

	@Override
	public String toString() {
		return "(" + child + ")";
	}

}
