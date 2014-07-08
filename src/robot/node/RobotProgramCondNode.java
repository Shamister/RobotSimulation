package robot.node;

import robot.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program
 * node
 */

public interface RobotProgramCondNode {

	public boolean evaluate(Robot robot);

	@Override
	public String toString();
}
