package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class that represents a piece of textual data. It inherits from Node
 * class.It is used for the purpouse of storing text form the document (i.e. the
 * text not written in between the tags). It allows escaping in form of
 * translating '\\' to '\' and '\{' to '{'. Other escape sequences are invalid.
 * 
 * @author Damjan Vučina
 */
public class TextNode extends Node {

	/** The text of the document. */
	private String text;

	/**
	 * Instantiates a new text node.
	 *
	 * @param text
	 *            the text of the document.
	 */
	public TextNode(String text) {
		// super();
		this.text = text;
	}

	/**
	 * Gets the text of the document..
	 *
	 * @return the text of the document.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Method used for notifying the visitor object
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	/**
	 * Method used for generating string representation of this text node.
	 */
	@Override
	public String toString() {
		return text;
	}
}
