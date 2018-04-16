package hr.fer.zemris.java.custom.scripting.exec;

public class ObjectMultistackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectMultistackException() {
		super();
	}

	public ObjectMultistackException(String s) {
		super(s);
	}

	public ObjectMultistackException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectMultistackException(Throwable cause) {
		super(cause);
	}

}
