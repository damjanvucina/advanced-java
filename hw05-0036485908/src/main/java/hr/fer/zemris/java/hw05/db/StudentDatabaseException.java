package hr.fer.zemris.java.hw05.db;

/**
 * The class that represents the exception which is thrown whenever the format
 * of the input text file that represents a database is not in compliance with
 * the specified format.
 * 
 * @author Damjan Vučina
 */
public class StudentDatabaseException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new student database exception .
	 */
	public StudentDatabaseException() {
		super();
	}

	/**
	 * Instantiates a new student database exception with the specified detail
	 * message.
	 *
	 * @param s
	 *            the specified detail message.
	 */
	public StudentDatabaseException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new student database exception with the provided warning
	 * message and cause.
	 *
	 * @param message
	 *            the detail message of the thrown exception that can be obtained
	 *            via getMessage() function
	 * @param cause
	 *            the detail cause of the thrown exception that can be obtained via
	 *            getCause() function
	 */
	public StudentDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new student database exception with the provided cause of the
	 * thrown exception.
	 *
	 * @param cause
	 *            the provided cause of the thrown exception.
	 */
	public StudentDatabaseException(Throwable cause) {
		super(cause);
	}

}
