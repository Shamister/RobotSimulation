package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class SENNode implements RobotProgramIntNode {

	final RobotProgramIntNode child;

	public SENNode(RobotProgramIntNode child) {
		this.child = child;
	}

	@Override
	public int evaluate(Robot robot) {
		return child.evaluate(robot);
	}

	@Override
	public String toString() {
		return child.toString();
	}

}
