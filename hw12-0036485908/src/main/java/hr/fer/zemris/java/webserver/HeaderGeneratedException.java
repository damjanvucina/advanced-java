package hr.fer.zemris.java.webserver;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the header filee is not in compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class HeaderGeneratedException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new header generated exception.
	 */
	public HeaderGeneratedException() {
		super();
	}

	/**
	 * Instantiates a new header generated exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public HeaderGeneratedException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new header generated exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public HeaderGeneratedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new header generated exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public HeaderGeneratedException(Throwable cause) {
		super(cause);
	}

}
