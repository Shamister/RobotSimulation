package robot.node.terminal.ifelse;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class IfNode implements RobotProgramCondNode {

	final RobotProgramCondNode cond;
	final RobotProgramNode block;

	public IfNode(RobotProgramCondNode cond, RobotProgramNode block) {
		this.cond = cond;
		this.block = block;
	}

	@Override
	public boolean evaluate(Robot robot) {
		if (cond.evaluate(robot)) {
			block.execute(robot);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "if (" + cond + ") " + block;
	}

}
