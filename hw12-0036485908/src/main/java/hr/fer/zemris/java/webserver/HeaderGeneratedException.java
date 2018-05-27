package hr.fer.zemris.java.webserver;

public class HeaderGeneratedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public HeaderGeneratedException() {
		super();
	}

	public HeaderGeneratedException(String s) {
		super(s);
	}

	public HeaderGeneratedException(String message, Throwable cause) {
		super(message, cause);
	}

	public HeaderGeneratedException(Throwable cause) {
		super(cause);
	}

}
