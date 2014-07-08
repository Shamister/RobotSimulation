package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class WHILENode implements RobotProgramNode {

	final RobotProgramCondNode cond;
	final RobotProgramNode block;

	public WHILENode(RobotProgramCondNode cond, RobotProgramNode block) {
		this.cond = cond;
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {
		while (cond.evaluate(robot)) {
			block.execute(robot);
		}
	}

	@Override
	public String toString() {
		return "while (" + cond + ") " + block;
	}

}
