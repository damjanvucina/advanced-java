package hr.fer.zemris.java.gui.calc;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the calculator input is not in compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class CalculatorException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calculator exception.
	 */
	public CalculatorException() {
		super();
	}

	/**
	 * Instantiates a new calculator exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public CalculatorException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new calculator exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new calculator exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public CalculatorException(Throwable cause) {
		super(cause);
	}

}
