package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramCondNode;
import robot.node.RobotProgramNode;

public class ShieldOnNode implements RobotProgramNode {

	final RobotProgramCondNode cond;

	public ShieldOnNode() {
		this.cond = null;
	}

	public ShieldOnNode(RobotProgramCondNode cond) {
		this.cond = cond;
	}

	@Override
	public void execute(Robot robot) {
		if (cond != null) {
			robot.setShield(cond.evaluate(robot));
		} else {
			robot.setShield(true);
		}
	}

	@Override
	public String toString() {
		if (cond != null) {
			return "shieldOn(" + cond + ")";
		}
		return "shieldOn";
	}

}
