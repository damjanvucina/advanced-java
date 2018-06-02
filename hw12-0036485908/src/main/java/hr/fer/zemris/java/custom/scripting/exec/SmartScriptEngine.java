package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * The class responsible for the execution of the previously parsed tree.
 * 
 * @author Damjan Vučina
 */
public class SmartScriptEngine {

	/** The document node. */
	private DocumentNode documentNode;

	/** The request context. */
	private RequestContext requestContext;

	/** The multistack. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** The reference to the visitor object that defines printing of the nodes. */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Prints TextNode.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Prints ForLoopNode.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().getName();

			String startValue = node.getStartExpression().asText();
			String stepValue = node.getStepExpression().asText();
			String endValue = node.getEndExpression().asText();

			multistack.push(variableName, new ValueWrapper(startValue));

			while (multistack.peek(variableName).numCompare(endValue) <= 0) {
				for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
					node.getChild(i).accept(this);
				}

				multistack.peek(variableName).add(stepValue);
			}

			multistack.pop(variableName);
		}

		/**
		 * Prints EchoNode.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> temporary = new Stack<>();

			for (Element currentElement : node.getElements()) {

				if (currentElement instanceof ElementConstantDouble) {
					temporary.push(((ElementConstantDouble) currentElement).getValue());

				} else if (currentElement instanceof ElementConstantInteger) {
					temporary.push(((ElementConstantInteger) currentElement).getValue());

				} else if (currentElement instanceof ElementString) {
					temporary.push(((ElementString) currentElement).asText());

				} else if (currentElement instanceof ElementVariable) {
					String variableName = ((ElementVariable) currentElement).getName();
					temporary.push(multistack.peek(variableName).getValue());

				} else if (currentElement instanceof ElementOperator) {
					ValueWrapper first = new ValueWrapper(temporary.pop());
					ValueWrapper second = new ValueWrapper(temporary.pop());

					ValueWrapper result = calculateResult(first, second, (ElementOperator) currentElement);

					temporary.push(result.getValue());
				} else if (currentElement instanceof ElementFunction) {
					performFunction((ElementFunction) currentElement, temporary);
				}
			}

			Collections.reverse(temporary);
			while (!temporary.isEmpty()) {
				Object value = temporary.pop();

				try {
					requestContext.write(String.valueOf(value));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		/**
		 * Method responsible for the identification and invocation of the functions.
		 * 
		 * @param function
		 * @param temporary
		 */
		private void performFunction(ElementFunction function, Stack<Object> temporary) {
			switch (function.getName()) {

			case "sin":
				performSin(temporary);
				break;

			case "decfmt":
				performDecfmt(temporary);
				break;

			case "dup":
				performDup(temporary);
				break;

			case "swap":
				performSwap(temporary);
				break;

			case "setMimeType":
				performSetMimeType(temporary);
				break;

			case "paramGet":
				performParamGet(temporary);
				break;

			case "pparamGet":
				performPParamGet(temporary);
				break;

			case "pparamSet":
				performPParamSet(temporary);
				break;

			case "pparamDel":
				performPParamDel(temporary);
				break;

			case "tparamGet":
				performTParamGet(temporary);
				break;

			case "tparamSet":
				performTParamSet(temporary);
				break;

			case "tparamDel":
				performTParamDel(temporary);
				break;

			default:
				throw new IllegalArgumentException("Unsupported function, function name was: " + function.getName());
			}
		}

		/**
		 * Deletes temporary parameter.
		 * 
		 * @param temporary
		 */
		private void performTParamDel(Stack<Object> temporary) {
			Object name = temporary.pop();
			requestContext.removeTemporaryParameter((String) name);
		}

		/**
		 * Sets temporary parameter.
		 * 
		 * @param temporary
		 */
		private void performTParamSet(Stack<Object> temporary) {
			Object name = temporary.pop();
			Object value = temporary.pop();

			requestContext.setTemporaryParameter(String.valueOf(name), String.valueOf(value));
		}

		/**
		 * Gets temporary parameter.
		 * 
		 * @param temporary
		 */
		private void performTParamGet(Stack<Object> temporary) {
			Object defValue = temporary.pop();
			Object name = temporary.pop();

			Object value = requestContext.getTemporaryParameter((String) name);
			temporary.push(value == null ? defValue : value);
		}

		/**
		 * Deletes persistent parameter.
		 * 
		 * @param temporary
		 */
		private void performPParamDel(Stack<Object> temporary) {
			Object name = temporary.pop();
			requestContext.removePersistentParameter((String) name);
		}

		/**
		 * Sets persistent parameter.
		 * 
		 * @param temporary
		 */
		private void performPParamSet(Stack<Object> temporary) {
			Object name = temporary.pop();
			Object value = temporary.pop();

			requestContext.setPersistentParameter((String) name, String.valueOf(value));
		}

		/**
		 * Gets persistent parameter.
		 * 
		 * @param temporary
		 */
		private void performPParamGet(Stack<Object> temporary) {
			Object defValue = temporary.pop();
			Object name = temporary.pop();

			Object value = requestContext.getPersistentParameter((String) name);
			temporary.push(value == null ? defValue : value);
		}

		/**
		 * Gets parameter.
		 * 
		 * @param temporary
		 */
		private void performParamGet(Stack<Object> temporary) {
			Object defValue = temporary.pop();
			Object name = temporary.pop();

			Object value = requestContext.getParameter((String) name);
			temporary.push(value == null ? defValue : value);
		}

		/**
		 * Sets mime type.
		 * 
		 * @param temporary
		 */
		private void performSetMimeType(Stack<Object> temporary) {
			requestContext.setMimeType((String) temporary.pop());
		}

		/**
		 * Swaps two entries from the given stack.
		 * 
		 * @param temporary
		 */
		private void performSwap(Stack<Object> temporary) {
			Object first = temporary.pop();
			Object second = temporary.pop();

			temporary.push(first);
			temporary.push(second);
		}

		/**
		 * Duplicates stack peek.
		 * 
		 * @param temporary
		 */
		private void performDup(Stack<Object> temporary) {
			temporary.push(temporary.peek());
		}

		/**
		 * Formats stack peek.
		 * 
		 * @param temporary
		 */
		private void performDecfmt(Stack<Object> temporary) {
			String pattern = (String) temporary.pop();
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			ValueWrapper wrapper = (ValueWrapper) temporary.pop();

			temporary.push(decimalFormat.format(wrapper.getValue()));
		}

		/**
		 * Calculates sine of the stack peek.
		 * 
		 * @param temporary
		 */
		private void performSin(Stack<Object> temporary) {
			Object value = temporary.pop();
			double result;

			if (value instanceof Integer) {
				result = sin(toRadians((Integer) value));
			} else {
				result = sin(toRadians((Double) value));
			}

			temporary.push(new ValueWrapper(result));
		}

		/**
		 * Performs mathematical operation over two ValueWrapper objects.
		 * 
		 * @param first
		 * @param second
		 * @param operator
		 * @return
		 */
		private ValueWrapper calculateResult(ValueWrapper first, ValueWrapper second, ElementOperator operator) {
			switch (operator.getSymbol()) {

			case "+":
				first.add(second.getValue());
				break;

			case "-":
				first.subtract(second.getValue());
				break;

			case "*":
				first.multiply(second.getValue());
				break;

			case "/":
				first.divide(second.getValue());
				break;

			default:
				throw new IllegalArgumentException("Invalid operator occured, was: " + operator.getSymbol());
			}

			return first;
		}

		/**
		 * Prints DocumentNode.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				Node currentNode = node.getChild(i);
				currentNode.accept(this);
			}

			System.out.println();
		}

	};

	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode
	 *            the document node
	 * @param requestContext
	 *            the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode, "Document node cannot be null.");
		Objects.requireNonNull(requestContext, "Request context node cannot be null.");

		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes this document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
