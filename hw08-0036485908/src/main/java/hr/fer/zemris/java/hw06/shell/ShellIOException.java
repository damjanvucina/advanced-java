package hr.fer.zemris.java.hw06.shell;

// TODO: Auto-generated Javadoc
/**
 * The class that represents the exception which is thrown whenever the format
 * of the MyShell input text is not in compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class ShellIOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ShellIOException exception .
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Instantiates a new ShellIOException with the specified detail message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public ShellIOException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new ShellIOException with the provided warning message and
	 * cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new ShellIOException with the provided cause of the thrown
	 * exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
}
