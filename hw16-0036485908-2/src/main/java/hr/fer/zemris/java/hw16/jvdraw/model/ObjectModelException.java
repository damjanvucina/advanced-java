package hr.fer.zemris.java.hw16.jvdraw.model;

public class ObjectModelException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new student database exception .
	 */
	public ObjectModelException() {
		super();
	}

	public ObjectModelException(String s) {
		super(s);
	}

	public ObjectModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectModelException(Throwable cause) {
		super(cause);
	}

}
