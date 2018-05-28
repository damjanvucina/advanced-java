package hr.fer.zemris.java.custom.scripting.elems;

/**
 * * The class that encapsulates document's functions' names that are processed
 * by the program's lexer. It inherits Element class, and as such provides
 * method for accessing the string representation of the function's name. Valid
 * function name starts with @ after which follows a letter and after than can
 * follow zero or more letters, digits or underscores. If function name is not
 * valid, it is invalid.
 * 
 * @author Damjan Vučina
 */
public class ElementFunction extends Element {

	/** The name of the function. */
	private String name;

	/**
	 * Instantiates a new element function.
	 *
	 * @param name
	 *            the name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Gets the name of the function.
	 *
	 * @return the name
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
