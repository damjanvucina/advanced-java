package hr.fer.zemris.java.p12.dao;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the interaction with the database is not in compliance with the specified
 * format.
 * 
 * @author Damjan Vučina
 */
public class DAOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new DAO exception.
	 */
	public DAOException() {
	}

	/**
	 * Instantiates a new DAO exception with the provided warning message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 * @param enableSuppression
	 *            the enable suppression flag defining whether the suppresion is to
	 *            take place
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new DAO exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new DAO exception with the specified detail message.
	 *
	 * @param message
	 *            the message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new DAO exception with the provided cause of the thrown
	 * exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}