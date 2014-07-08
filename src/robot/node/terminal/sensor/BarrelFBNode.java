package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class BarrelFBNode implements RobotProgramIntNode {

	final RobotProgramIntNode expr;

	public BarrelFBNode() {
		this.expr = null;
	}

	public BarrelFBNode(RobotProgramIntNode expr) {
		this.expr = expr;
	}

	@Override
	public int evaluate(Robot robot) {
		if (expr != null) {
			return robot.getBarrelFB(expr.evaluate(robot));
		}
		return robot.getClosestBarrelFB();
	}

	@Override
	public String toString() {
		if (expr != null) {
			return "barrelFB(" + expr + ")";
		}
		return "barrelFB";
	}

}
