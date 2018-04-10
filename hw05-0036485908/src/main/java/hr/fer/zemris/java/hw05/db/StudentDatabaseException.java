package hr.fer.zemris.java.hw05.db;

public class StudentDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StudentDatabaseException() {
		super();
	}

	public StudentDatabaseException(String s) {
		super(s);
	}

	public StudentDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public StudentDatabaseException(Throwable cause) {
		super(cause);
	}

}

