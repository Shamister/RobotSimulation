package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramNode;

public class ShieldOffNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(false);
	}

	@Override
	public String toString() {
		return "shieldOff";
	}

}
