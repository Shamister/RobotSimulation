package robot;

import java.util.HashMap;
import java.util.Map;

import robot.node.RobotProgramIntNode;

public class BLOCK {

	Map<String, RobotProgramIntNode> varMap;
	BLOCK innerBlock, outerBlock;

	public BLOCK() {
		varMap = new HashMap<String, RobotProgramIntNode>();
	}

	public void setInnerBlock(BLOCK innerBlock) {
		this.innerBlock = innerBlock;
	}

	public void setOuterBlock(BLOCK outerBlock) {
		this.outerBlock = outerBlock;
	}

	public void addVar(String name, RobotProgramIntNode var) {
		varMap.put(name, var);
	}

	public boolean containsVarThisBlock(String name) {
		return getVarThisBlock(name) != null;
	}

	public boolean containsVarOuterBlock(String name) {
		return getVarOuterBlock(name) != null;
	}

	public boolean containsVarInnerBlock(String name) {
		return getVarInnerBlock(name) != null;
	}

	public RobotProgramIntNode getVarThisBlock(String name) {
		return varMap.get(name);
	}

	public RobotProgramIntNode getVarOuterBlock(String name) {
		if (outerBlock != null) {
			RobotProgramIntNode var = outerBlock.getVarThisBlock(name);
			if (var != null)
				return var;
			return outerBlock.getVarOuterBlock(name);
		}
		return null;
	}

	public RobotProgramIntNode getVarInnerBlock(String name) {
		if (innerBlock != null) {
			RobotProgramIntNode var = innerBlock.getVarThisBlock(name);
			if (var != null)
				return var;
			return innerBlock.getVarOuterBlock(name);
		}
		return null;
	}

}
