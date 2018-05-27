package hr.fer.zemris.java.custom.scripting.elems;

/**
 * * The class that encapsulates document's variable names that are processed by
 * the program's lexer. It inherits Element class, and as such provides method
 * for accessing their String representations. Valid variable name starts by
 * letter and after follows zero or more letters, digits or underscores. If name
 * is not valid, it is invalid. This variable names are valid: A7_bb, counter,
 * tmp_34; these are not: _a21, 32, 3s_ee etc.
 */
public class ElementVariable extends Element {

	/** The name of the variable. */
	private String name;

	/**
	 * Instantiates a new element variable.
	 *
	 * @param name
	 *            the name of the variable.
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	/**
	 * Gets the name of the variable..
	 *
	 * @return the name of the variable.
	 */
	public String getName() {
		return name;
	}

	/*
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return getName();
	}
}
