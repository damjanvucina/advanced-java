package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;
import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The base class for all the graph nodes. It is used for storing elements of
 * the document as instances of Element class( or of classes derived from it)
 * for the purpose of creating a tree of the elements of the document. It
 * provides methods for adding nodes to internally managed collection, counting
 * the number of the children elements and printing node children in the
 * specified form.
 * 
 * @author Damjan Vučina
 */
public class Node {

	/** The collection of the nodes of the document. */
	ArrayIndexedCollection collection;

	/**
	 * Adds the child node to the internally managed collection of objects.
	 *
	 * Complexity: O(1)
	 * 
	 * @param child
	 *            the child node that is being added to the internally managed
	 *            collection of objects.
	 */
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}

		collection.add(child);
	}

	/**
	 * Gets the number of children in internally managed collection of objects.
	 *
	 * @return the number of children in internally managed collection of objects.
	 */
	public int numberOfChildren() {
		return collection.size();
	}

	/**
	 * Gets the child node at the specified position in internally managed
	 * collection of objects.
	 *
	 * @param index
	 *            the index of the child node at the specified position in
	 *            internally managed collection of objects.
	 * @return the child node at the specified position in internally managed
	 *         collection of objects.
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}

	/**
	 * Prints the children nodes from the internallny managed collection of objects.
	 * Method delegates to other helper methods depending on the instance of the
	 * node that is being printed.
	 *
	 * @return the string representation of the tree that consists of all of the elements of the document
	 */
	public String printChildrenNodes() {
		StringBuilder sb = new StringBuilder();

		if (collection != null) {
			for (Object obj : collection.toArray()) {

				if (obj instanceof TextNode) {
					sb.append(getTextNodeRepresentation(obj));
				} else if (obj instanceof ForLoopNode) {

					ForLoopNode forLoopNode = (ForLoopNode) obj;
					sb.append(getForLoopNodeRepresentation(obj));
					sb.append(forLoopNode.printChildrenNodes());
					sb.append("{$END$}");

				} else if (obj instanceof EchoNode) {
					sb.append(getEchoNodeRepresentation(obj));
				} else {
					throw new SmartScriptParserException(
							"Element of class Node's collection is an instance of an unsupported class.");
				}

			}
		}
		return sb.toString();
	}

	/**
	 * Gets the String representation of the Echo node.
	 *
	 * @param obj
	 *            Echo node whose children nodes are to be printed
	 * @return the the String representation of the Echo node.
	 */
	private String getEchoNodeRepresentation(Object obj) {
		obj = Objects.requireNonNull(obj, "Element of class Node's collection is an instance of an unsupported class.");

		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");

		for (Element element : ((EchoNode) obj).getElements()) {
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

	/**
	 * Gets the String representation of the ForLoop node.
	 *
	 * @param obj
	 *            ForLoop node whose children nodes are to be printed
	 * @return the the String representation of the ForLoop node.
	 */
	private String getForLoopNodeRepresentation(Object obj) {
		obj = Objects.requireNonNull(obj, "Element of Node's collection is an instance of unsupported class.");

		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(((ForLoopNode) obj).getVariable().asText()).append(" ");
		sb.append(((ForLoopNode) obj).getStartExpression().asText()).append(" ");
		sb.append(((ForLoopNode) obj).getEndExpression().asText()).append(" ");

		if (((ForLoopNode) obj).getStepExpression() != null) {
			sb.append(((ForLoopNode) obj).getStepExpression().asText()).append(" ");
		}
		sb.append(" $}");

		return sb.toString();
	}

	/**
	 * Gets the String representation of the TextNode.
	 *
	 * @param obj
	 *            TextNode node whose children nodes are to be printed
	 * @return the the String representation of the TextNode node.
	 */
	private String getTextNodeRepresentation(Object obj) {
		obj = Objects.requireNonNull(obj, "Element of Node's collection is an instance of unsupported class.");

		return ((TextNode) obj).getText();
	}

}
