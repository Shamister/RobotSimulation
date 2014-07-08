package robot.node.terminal.ifelse;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class ElseNode implements RobotProgramCondNode {

	final RobotProgramNode block;

	public ElseNode(RobotProgramNode block) {
		this.block = block;
	}

	@Override
	public boolean evaluate(Robot robot) {
		block.execute(robot);
		return true;
	}

	@Override
	public String toString() {
		return "else" + block;
	}

}
