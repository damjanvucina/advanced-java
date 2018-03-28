package hr.fer.zemris.java.custom.scripting.elems;

/**
 * * The class that encapsulates document's Strings that are processed by the
 * program's lexer. It inherits Element class, and as such provides method for
 * accessing their representations. Escaping is permitted in due form: \\
 * sequence treat as a single string character \ \" treat as a single string
 * character " (and not the end of the string) \n, \r and \t have its usual
 * meaning
 */
public class ElementString extends Element {

	/** The value of the String. */
	private String value;

	/**
	 * Instantiates a new element string.
	 *
	 * @param value
	 *            the value of the String.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Gets the value of the String..
	 *
	 * @return the value of the String.
	 */
	private String getValue() {
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
