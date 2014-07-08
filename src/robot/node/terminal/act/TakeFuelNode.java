package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramNode;

public class TakeFuelNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.takeFuel();
	}

	@Override
	public String toString() {
		return "takeFuel";
	}
}
