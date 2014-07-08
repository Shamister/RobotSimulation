package robot.node.terminal.op;

import robot.Robot;
import robot.RobotInterruptedException;
import robot.node.RobotProgramIntNode;

public class DivNode implements RobotProgramIntNode {

	RobotProgramIntNode expr1;
	RobotProgramIntNode expr2;

	public DivNode(RobotProgramIntNode expr1, RobotProgramIntNode expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public int evaluate(Robot robot) {
		try {
			return expr1.evaluate(robot) / expr2.evaluate(robot);
		}
		// catch error of division by zero
		catch (ArithmeticException e) {
			System.out.println("Runtime Error : Division by zero\n @...div(" + expr1 + ", "
					+ expr2 + ")...");
			throw new RobotInterruptedException("Runtime Error : Division by zero\n @...div("
					+ expr1 + ", " + expr2 + ")...");
		}
	}

	@Override
	public String toString() {
		return expr1 + " / " + expr2;
	}
}
