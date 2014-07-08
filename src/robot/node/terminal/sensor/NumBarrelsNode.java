package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class NumBarrelsNode implements RobotProgramIntNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.numBarrels();
	}

	@Override
	public String toString() {
		return "numBarrels";
	}

}
