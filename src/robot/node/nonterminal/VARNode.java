package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramIntNode;

public class VARNode implements RobotProgramIntNode {

	final String name;
	RobotProgramIntNode expr;

	public VARNode(String name, RobotProgramIntNode expr) {
		this.name = name;
		this.expr = expr;
	}

	@Override
	public int evaluate(Robot robot) {
		return expr.evaluate(robot);
	}

	public void setValue(RobotProgramIntNode expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return name.toString();
	}

}
