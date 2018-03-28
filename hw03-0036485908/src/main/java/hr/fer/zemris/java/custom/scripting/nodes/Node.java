package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;
import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class Node {

	ArrayIndexedCollection collection;

	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}

		collection.add(child);
	}

	public int numberOfChildren() {
		return collection.size();
	}

	public Node getChild(int index) {
		return (Node) collection.get(index);
	}

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

	private String getTextNodeRepresentation(Object obj) {
		obj = Objects.requireNonNull(obj, "Element of Node's collection is an instance of unsupported class.");

		return ((TextNode) obj).getText();
	}

}
