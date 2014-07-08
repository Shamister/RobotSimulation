package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramNode;

public class ACTNode implements RobotProgramNode {

	final RobotProgramNode child;

	public ACTNode(RobotProgramNode child) {
		this.child = child;
	}

	@Override
	public void execute(Robot robot) {
		child.execute(robot);
	}

	@Override
	public String toString() {
		return child + ";";
	}

}
