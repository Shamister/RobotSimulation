package robot.node.nonterminal;

import java.util.List;

import robot.Robot;
import robot.node.RobotProgramNode;

public class PROGNode implements RobotProgramNode {

	final List<RobotProgramNode> children;

	public PROGNode(List<RobotProgramNode> children) {
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
		String command = "";
		for (int i = 0; i < children.size(); i++) {
			RobotProgramNode child = children.get(i);
			if (child != null) {
				command += child.toString();
				if (children.size() - 1 - i > 0) {
					command += "\n";
				}
			}
		}
		return command;
	}
}
