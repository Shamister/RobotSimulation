package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class NUMNode implements RobotProgramIntNode {

	final int value;

	public NUMNode(int value) {
		this.value = value;
	}

	@Override
	public int evaluate(Robot robot) {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}

}
