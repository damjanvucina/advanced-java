package hr.fer.zemris.java.hw16.jvdraw.model;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectModelException.
 */
public class ObjectModelException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new student database exception .
	 */
	public ObjectModelException() {
		super();
	}

	/**
	 * Instantiates a new object model exception.
	 *
	 * @param s the s
	 */
	public ObjectModelException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new object model exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ObjectModelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new object model exception.
	 *
	 * @param cause the cause
	 */
	public ObjectModelException(Throwable cause) {
		super(cause);
	}

}
