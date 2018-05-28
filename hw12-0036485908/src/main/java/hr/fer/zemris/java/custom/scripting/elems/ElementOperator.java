package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class that encapsulates document's operators' symbols that are processed
 * by the program's lexer. It inherits Element class, and as such provides
 * method for accessing the string representation of the symbol. Valid operators
 * are + (plus), - (minus), * (multiplication), / (division), ^ (power).
 * 
 * @author Damjan Vučina
 */
public class ElementOperator extends Element {

	/** The String representation of the symbol. */
	private String symbol;

	/**
	 * Instantiates a new element operator identical to the one provided as the
	 * argument.
	 *
	 * @param symbol
	 *            the symbol argument
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Gets the symbol's string representation.
	 *
	 * @return the symbol's string representation.
	 */
	public String getSymbol() {
		return symbol;
	}

	/*
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return String.valueOf(getSymbol());
	}
}
