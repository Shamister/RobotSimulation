package robot.node.nonterminal;

import robot.Robot;
import robot.node.RobotProgramCondNode;

public class BOOLNode implements RobotProgramCondNode {

	final boolean cond;

	public BOOLNode(boolean cond) {
		this.cond = cond;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return cond;
	}

	@Override
	public String toString() {
		return "" + cond;
	}

}
