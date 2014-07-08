package robot.node.terminal.act;

import robot.Robot;
import robot.node.RobotProgramIntNode;
import robot.node.RobotProgramNode;

public class MoveNode implements RobotProgramNode {

	final RobotProgramIntNode expr;

	public MoveNode() {
		expr = null;
	}

	public MoveNode(RobotProgramIntNode node) {
		expr = node;
	}

	@Override
	public void execute(Robot robot) {
		if (expr == null) {
			robot.move();
		} else {
			int count = expr.evaluate(robot);
			for (int i = 0; i < count; i++) {
				robot.move();
			}
		}
	}

	@Override
	public String toString() {
		if (expr != null) {
			return "move(" + expr + ")";
		}
		return "move";
	}

}
