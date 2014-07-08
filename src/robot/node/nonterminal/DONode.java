package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class DONode implements RobotProgramNode {

	final RobotProgramCondNode cond;
	final RobotProgramNode block;

	public DONode(RobotProgramNode block, RobotProgramCondNode cond) {
		this.block = block;
		this.cond = cond;
	}

	@Override
	public void execute(Robot robot) {
		do {
			block.execute(robot);
		} while (cond.evaluate(robot));
	}

	@Override
	public String toString() {
		return "do " + block + " while(" + cond + ")";
	}

}
