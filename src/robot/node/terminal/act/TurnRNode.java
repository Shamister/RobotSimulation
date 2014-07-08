package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramNode;

public class TurnRNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnRight();
	}

	@Override
	public String toString() {
		return "turnR";
	}

}
