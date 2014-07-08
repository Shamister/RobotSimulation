package robot.node;

import robot.Robot;

public class ParenIntNode implements RobotProgramIntNode {

	final RobotProgramIntNode child;

	public ParenIntNode(RobotProgramIntNode child) {
		this.child = child;
	}

	@Override
	public int evaluate(Robot robot) {
		return child.evaluate(robot);
	}

	@Override
	public String toString() {
		return "(" + child + ")";
	}

}
