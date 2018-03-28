package hr.fer.zemris.java.hw03.prob1;

public class LexerException extends RuntimeException {
	/**
	 * The class that represents the exception that is thrown whenever the lexer
	 * comes across any irregularity that is not in conformance to the defined rules
	 * of character grouping.
	 * 
	 * @author Damjan Vučina
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an LexerException with no detail message.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructs an LexerException with the specified detail message.
	 *
	 * @param s
	 *            the detail message.
	 */
	public LexerException(String s) {
		super(s);
	}

	/**
	 * Construct a new exception with the provided warning message and cause
	 *
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the provided cause of the thrown exception
	 *
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
}
