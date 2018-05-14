package hr.fer.zemris.java.gui.calc;

public class CalculatorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CalculatorException() {
		super();
	}


	public CalculatorException(String s) {
		super(s);
	}


	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
	}


	public CalculatorException(Throwable cause) {
		super(cause);
	}

}
