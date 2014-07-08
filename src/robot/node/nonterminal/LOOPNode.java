package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramNode;

public class LOOPNode implements RobotProgramNode {

	final RobotProgramNode child;

	public LOOPNode(RobotProgramNode node) {
		child = node;
	}

	@Override
	public void execute(Robot robot) {
		while (true) {
			child.execute(robot);
		}
	}

	@Override
	public String toString() {
		return "loop " + child.toString();
	}

}
