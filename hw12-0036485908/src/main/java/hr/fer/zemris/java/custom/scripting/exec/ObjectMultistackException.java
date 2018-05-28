package hr.fer.zemris.java.custom.scripting.exec;

/**
 * The class that represents the exception which is thrown whenever the
 * ObjectMultistack class is misused or its method arguments are not in
 * compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class ObjectMultistackException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new object multistack exception.
	 */
	public ObjectMultistackException() {
		super();
	}

	/**
	 * Instantiates a new multistack exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public ObjectMultistackException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new multistack exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public ObjectMultistackException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new multistack exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public ObjectMultistackException(Throwable cause) {
		super(cause);
	}

}
