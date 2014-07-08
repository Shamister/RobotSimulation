package robot.node;

import robot.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program
 * node
 */

public interface RobotProgramIntNode {

	public int evaluate(Robot robot);

	@Override
	public String toString();
}
