package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramNode;

public class TurnLNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnLeft();
	}

	@Override
	public String toString() {
		return "turnL";
	}

}
