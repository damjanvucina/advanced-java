package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class that encapsulates document's double elements that are processed by
 * the program's lexer. It inherits Element class, and as such provides method
 * for accessing the double value of the element and its string representation.
 * 
 * @author Damjan Vučina
 */
public class ElementConstantDouble extends Element {

	/** The value of the element of the document. */
	private double value;

	/**
	 * Instantiates a new element constant double.
	 *
	 * @param value
	 *            The value of the element of the document
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/**
	 * Gets the value of the element of the document.
	 *
	 * @return the value of the element of the document
	 */
	public double getValue() {
		return value;
	}

	/*
	 *  
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
