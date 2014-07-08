package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class EXPNode implements RobotProgramIntNode {

	final RobotProgramIntNode child;
	final Integer value;

	public EXPNode(RobotProgramIntNode child) {
		this.child = child;
		value = null;
	}

	public EXPNode(int value) {
		this.child = null;
		this.value = value;
	}

	@Override
	public int evaluate(Robot robot) {
		if (child != null) {
			return child.evaluate(robot);
		}
		return value;
	}

	@Override
	public String toString() {
		return child.toString();
	}

}
