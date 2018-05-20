package hr.fer.zemris.java.hw11.jnotepadpp;

public class JNotepadException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public JNotepadException() {
		super();
	}

	public JNotepadException(String s) {
		super(s);
	}


	public JNotepadException(String message, Throwable cause) {
		super(message, cause);
	}

	public JNotepadException(Throwable cause) {
		super(cause);
	}
}
