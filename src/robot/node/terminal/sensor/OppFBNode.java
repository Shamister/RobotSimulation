package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class OppFBNode implements RobotProgramIntNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentLR();
	}

	@Override
	public String toString() {
		return "oppFB";
	}

}
