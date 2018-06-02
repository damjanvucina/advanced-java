package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * The class node representing a command which generates some textual output
 * dynamically. It inherits from Node class. It is definded by the '=' tag or
 * any other valid variable name; Valid variable name starts by letter and after
 * follows zero or more letters, digits or underscores. If name is not valid, it
 * is invalid. This variable names are valid: A7_bb, counter, tmp_34; these are
 * not: _a21, 32, 3s_ee etc. Other valid elements are integer, double, function
 * (which is defined by '@' symbol prior to valid variable name), String and
 * operator. It allows escaping in Strings in form of translating '\\' to '\'
 * and '\"' to '"'. \n, \r and \t have its usual meaning (ascii 10, 13 and 9).
 * Other escape sequences are invalid.
 * 
 * @author Damjan Vučina
 */
public class EchoNode extends Node {

	/**
	 * The elements array which is used for the purpose of storing elements of this
	 * node.
	 */
	private Element[] elements;

	/**
	 * Instantiates a new echo node.
	 *
	 * @param elements
	 *            the elements of this node.
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Gets the elements of this node.
	 *
	 * @return the elements of this node
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Method used for notifying the visitor object
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 * Method used for generating string representation of this echo node.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");

		for (Element element : getElements()) {
			if (element instanceof ElementFunction) {
				sb.append("@").append(element.asText());

			} else if (element instanceof ElementString) {
				sb.append("\"").append(element.asText()).append("\"");
			} else {
				sb.append(element.asText());
			}
			sb.append(" ");
		}
		sb.append(" $}");

		return sb.toString();
	}
}
