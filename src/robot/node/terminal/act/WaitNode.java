package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramIntNode;
import robot.node.RobotProgramNode;

public class WaitNode implements RobotProgramNode {

	final RobotProgramIntNode value;

	public WaitNode() {
		value = null;
	}

	public WaitNode(RobotProgramIntNode node) {
		value = node;
	}

	@Override
	public void execute(Robot robot) {
		if (value != null) {
			int delay = value.evaluate(robot);
			for (int i = 0; i < delay; i++) {
				robot.idleWait();
			}
		} else {
			robot.idleWait();
		}
	}

	@Override
	public String toString() {
		if (value != null)
			return "wait(" + value + ")";
		return "wait";
	}

}
