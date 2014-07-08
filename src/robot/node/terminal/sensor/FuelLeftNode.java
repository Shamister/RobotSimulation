package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class FuelLeftNode implements RobotProgramIntNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getFuel();
	}

	@Override
	public String toString() {
		return "fuelLeft";
	}

}
