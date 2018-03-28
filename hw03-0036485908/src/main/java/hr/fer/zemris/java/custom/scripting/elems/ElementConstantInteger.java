package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class that encapsulates document's integer elements that are processed by
 * the program's lexer. It inherits Element class, and as such provides method
 * for accessing the integer value of the element and its string representation.
 * 
 * @author Damjan Vučina
 */
public class ElementConstantInteger extends Element {

	/** The value of the element of the document. */
	private int value;
	
	/**
	 * Instantiates a new element constant integer.
	 *
	 * @param value the value of the element of the document. */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Gets the value of the element of the document.
	 *
	 * @return the value
	 */
	private int getValue() {
		return value;
	}
	
	/* 
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
