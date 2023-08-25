package id208322966_id207082280;

public class AddAnswersException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddAnswersException(String msg) {
		super(msg);
	}

	public AddAnswersException() {
		super("Adding Answers Exception: exceeds the size of the array.");
	}

}
