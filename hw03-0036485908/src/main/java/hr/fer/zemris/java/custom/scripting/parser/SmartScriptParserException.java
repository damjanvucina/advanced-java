package hr.fer.zemris.java.custom.scripting.parser;

/**
 * The class that represents the exception which is thrown whenever the parser
 * comes across the irregularity in tokens use or their grouping.
 * 
 * @author Damjan Vučina
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an <code>EmptyStackException</code> with no detail message.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructs an <code>EmptyStackException</code> with the specified detail
	 * message.
	 *
	 * @param s
	 *            the detail message.
	 */
	public SmartScriptParserException(String s) {
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
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the provided cause of the thrown exception
	 *
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
}
