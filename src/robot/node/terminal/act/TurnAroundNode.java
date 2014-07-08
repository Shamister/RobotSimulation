package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramNode;

public class TurnAroundNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnAround();
	}

	@Override
	public String toString() {
		return "turnAround";
	}

}
