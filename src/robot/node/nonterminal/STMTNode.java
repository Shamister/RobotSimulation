package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramNode;

public class STMTNode implements RobotProgramNode {

	final RobotProgramNode child;

	public STMTNode(RobotProgramNode node) {
		child = node;
	}

	@Override
	public void execute(Robot robot) {
		child.execute(robot);
	}

	@Override
	public String toString() {
		if (child != null) {
			return child.toString();
		}
		return "";
	}

}
