package hr.fer.zemris.java.custom.scripting.lexer;

public class SmartScriptLexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an <code>EmptyStackException</code> with no detail message.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructs an <code>EmptyStackException</code> with the specified detail
	 * message.
	 *
	 * @param s
	 *            the detail message.
	 */
	public SmartScriptLexerException(String s) {
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
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the provided cause of the thrown exception
	 *
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}

}
