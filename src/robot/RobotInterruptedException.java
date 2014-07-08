package robot;

public class RobotInterruptedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RobotInterruptedException() {
		super();
	}

	public RobotInterruptedException(String msg) {
		super(msg);
	}
}
