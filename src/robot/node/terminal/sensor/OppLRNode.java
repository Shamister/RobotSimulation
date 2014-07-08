package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class OppLRNode implements RobotProgramIntNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentFB();
	}

	@Override
	public String toString() {
		return "oppLR";
	}

}
