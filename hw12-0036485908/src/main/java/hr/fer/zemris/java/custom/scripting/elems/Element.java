package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The base class that encapsulates document's elements that are processed by
 * the program's lexer. It does not provide much functionality in terms of
 * having many methods. Rather it serves as a contract for the classes
 * inheriting it since it is never insantiated.
 * 
 * @author Damjan Vučina
 * 
 */
public class Element {

	/**
	 * Method that returns String representation of an element of the document.
	 * Notice: Here it is implemented to always return an empty String.
	 *
	 * @return the string representation of an element of the document
	 */
	public String asText() {
		return "";
	}

}
