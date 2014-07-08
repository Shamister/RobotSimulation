package robot.node.nonterminal;

import java.util.List;

import robot.Robot;
import robot.node.RobotProgramNode;

public class BLOCKNode implements RobotProgramNode {

	List<RobotProgramNode> children;

	public BLOCKNode(List<RobotProgramNode> children) {
		this.children = children;
	}

	@Override
	public void execute(Robot robot) {
		for (RobotProgramNode child : children) {
			child.execute(robot);
		}
	}

	@Override
	public String toString() {
		String syntax = "{\n";
		for (RobotProgramNode child : children) {
			syntax += "  " + child + "\n";
		}
		return syntax + "}";
	}

}
