package hr.fer.zemris.java.gui.layouts;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the calculator input is not in compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class CalcLayoutException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calculator layout exception.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Instantiates a new calculator layout exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public CalcLayoutException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new calculator layout exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new calculator layout exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
}
