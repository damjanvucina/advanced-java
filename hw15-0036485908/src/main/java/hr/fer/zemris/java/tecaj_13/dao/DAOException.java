package hr.fer.zemris.java.tecaj_13.dao;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the database input is not in compliance with the specified format.
 * 
 * @author Damjan Vučina
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public DAOException(String message) {
		super(message);
	}
}