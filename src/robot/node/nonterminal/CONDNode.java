package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramCondNode;

public class CONDNode implements RobotProgramCondNode {

	final RobotProgramCondNode child;

	public CONDNode(RobotProgramCondNode child) {
		this.child = child;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return child.evaluate(robot);
	}

	@Override
	public String toString() {
		return child.toString();
	}

}
