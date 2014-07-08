package robot.node.terminal.sensor;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class BarrelLRNode implements RobotProgramIntNode {

	final RobotProgramIntNode expr;

	public BarrelLRNode() {
		this.expr = null;
	}

	public BarrelLRNode(RobotProgramIntNode expr) {
		this.expr = expr;
	}

	@Override
	public int evaluate(Robot robot) {
		if (expr != null) {
			return robot.getBarrelLR(expr.evaluate(robot));
		}
		return robot.getClosestBarrelLR();
	}

	@Override
	public String toString() {
		if (expr != null) {
			return "barrelLR(" + expr + ")";
		}
		return "barrelLR";
	}

}
