package one.main;

/**
 * The Class MyException.
 */
@SuppressWarnings("serial")
public class MyException extends Exception {

	/**
	 * Instantiates a new x exception.
	 */
	public MyException() {
	}

	/**
	 * Instantiates a new x exception.
	 * 
	 * @param message
	 *            the message
	 */
	public MyException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new x exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MyException(String message, Throwable cause) {
		super(message, cause);
	}

}
