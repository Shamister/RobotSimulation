package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class WallDistNode implements RobotProgramIntNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getDistanceToWall();
	}

	@Override
	public String toString() {
		return "wallDist";
	}

}
