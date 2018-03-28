package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * * The class that represents an object that instantiates a lexer, provides it
 * with text input and invokes his methods for the purpose of acquiring new
 * tokens from text and creating a tree from those elements according to the
 * predefined set of rules.
 * 
 * @author Damjan Vučina
 */
public class SmartScriptParser {

	/** The lexer that is used for extracting tokens from the text. */
	SmartScriptLexer lexer;

	/** The stack used for ordering elements. */
	ObjectStack stack;

	/** Tree representation of the document */
	DocumentNode documentNode;

	/**
	 * Instantiates a new smart script parser.
	 *
	 * @param document
	 *            the document provided for processing
	 */
	public SmartScriptParser(String document) {
		lexer = new SmartScriptLexer(document);
		stack = new ObjectStack();
		documentNode = new DocumentNode();

		stack.push(documentNode);

		try {
			parse();
		} catch (EmptyStackException exc) {
			throw new SmartScriptParserException(exc.getCause());
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Invalid number of closing END tags, was: " + stack.size());
		}
	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Parses the document by delegating its work do the methods of class lexer.
	 * Then parses those elements and invokes other methods for the purpose of
	 * creation a tree of the elements of the document. Catches exceptions thrown
	 * from the lexer and encapsulates them to a specific parser exception.
	 * 
	 * @throws SmartScriptParserException
	 *             if an irregularity with token grouping is stumbled upon
	 */

	public void parse() {
		SmartScriptToken token = null;

		token = fetchNextToken();

		while (token.getType() != SmartScriptTokenType.EOF) {
			if (token.getType() == SmartScriptTokenType.TEXT) {

				if (!token.getValue().equals("")) {
					parseText(token);
				}

			} else if (token.getType() == SmartScriptTokenType.TAG_START) {
				parseTag();
			}

			token = fetchNextToken();
		}
	}

	/**
	 * Helper method that gets the next token.
	 *
	 * @return the smart script token * @throws SmartScriptParserException if an
	 *         irregularity with token grouping is stumbled upon
	 */
	private SmartScriptToken fetchNextToken() {
		try {
			return lexer.nextToken();
		} catch (SmartScriptLexerException exc) {
			throw new SmartScriptParserException(exc.getCause());
		}
	}

	/**
	 * Parses the tag that is stumbled upon by lexer.
	 */
	private void parseTag() {

		SmartScriptToken token = null;

		token = fetchNextToken();// acquire new token after TAG_START

		switch (token.getValue().toString()) {

		case "FOR":
			parseFor();
			break;

		case "END":
			parseEnd();
			break;

		case "=":
			parseEcho();
			break;

		default:

			if (token.getType() == SmartScriptTokenType.VARIABLE) {
				parseEcho();
			} else if (token.getType() == SmartScriptTokenType.EOF) {
				return;
			} else {
				throw new SmartScriptLexerException("Invalid tag name");
			}
		}
	}

	/**
	 * Parses the end tag that is stumbled upon by lexer.
	 */
	private void parseEnd() {
		stack.pop();

		fetchNextToken();
		confirmClosingTag("END");
	}

	/**
	 * Parses the echo tag that is stumbled upon by lexer.
	 */
	private void parseEcho() {
		Element[] echoTokens;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		SmartScriptToken currentToken = fetchNextToken();
		while (currentToken.getType() != SmartScriptTokenType.TAG_END) {
			collection.add(currentToken);
			currentToken = fetchNextToken();
		}

		int collectionSize = collection.size();
		echoTokens = new Element[collectionSize];

		for (int i = 0; i < collectionSize; i++) {
			echoTokens[i] = identifyTokenType((SmartScriptToken) (collection.toArray()[i]));
		}

		EchoNode echoNode = new EchoNode(echoTokens);
		Node parent = (Node) stack.pop();
		parent.addChildNode(echoNode);
		stack.push(parent);

		confirmClosingTag("ECHO");
	}

	/**
	 * Confirms the existence of a closing tag after processing all the elements of
	 * the given tag.
	 *
	 * @param tag
	 *            the tag that is being processed
	 */
	private void confirmClosingTag(String tag) {
		if (lexer.getToken().getType() != SmartScriptTokenType.TAG_END) {
			throw new SmartScriptLexerException(tag + " tag was not properly closed.");
		}

	}

	/**
	 * Parses the for tag that is stumbled upon by lexer.
	 * 
	 * @throws SmartScriptParserException
	 *             if invalid number of arguments within FOR tag occurs.
	 */
	private void parseFor() {
		ArrayIndexedCollection forTokens = new ArrayIndexedCollection(4);

		ElementVariable variable = null;
		Element startExpression = null;
		Element endExpression = null;
		Element stepExpression = null;

		SmartScriptToken currentToken = fetchNextToken();

		while (currentToken.getType() != SmartScriptTokenType.TAG_END) {
			forTokens.add(currentToken);
			currentToken = fetchNextToken();
		}

		int forTokensSize = forTokens.size();
		if (forTokensSize < 3 || forTokensSize > 4) {
			throw new SmartScriptParserException(
					"Invalid number of arguments within FOR tag, must be either 3 or 4, was: " + forTokens.size());
		} else {

			for (int i = 0; i < forTokensSize; i++) {
				currentToken = (SmartScriptToken) forTokens.get(i);

				switch (i) {
				case 0:
					variable = (ElementVariable) identifyTokenType(currentToken);
					verifyVariableTokenType(variable);
					break;

				case 1:
					startExpression = identifyTokenType(currentToken);
					verifyExpressionTokenType(startExpression);
					break;

				case 2:
					endExpression = identifyTokenType(currentToken);
					verifyExpressionTokenType(endExpression);
					break;

				case 3:
					stepExpression = identifyTokenType(currentToken);
					verifyExpressionTokenType(stepExpression);
					break;

				default:
					break;
				}
			}

			ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

			Node parent = (Node) stack.pop();
			parent.addChildNode(forLoopNode);
			stack.push(parent);
			stack.push(forLoopNode);

			confirmClosingTag("FOR");
		}
	}

	/**
	 * Verifies token types of the expressions in the FOR tag.
	 *
	 * @param expression
	 *            the expression
	 */
	private void verifyExpressionTokenType(Element expression) {
		if (expression instanceof ElementFunction || expression instanceof ElementOperator) {
			throw new SmartScriptParserException("Expression in FOR tag is an instance of unsupported class");
		}
	}

	/**
	 * Verifies token type of the elementVariable in the FOR tag.
	 *
	 * @param variable
	 *            the variable
	 */
	private void verifyVariableTokenType(Element variable) {
		if (!(variable instanceof ElementVariable)) {
			throw new SmartScriptParserException(
					"First element of FOR tag must be an instance of ElementVariable class");
		}
	}

	/**
	 * Identifies the token type of the token that is to be sent for further
	 * processing.
	 *
	 * @param currentToken
	 *            the current token
	 * @return encapsulation of such token in a class derived from class Element
	 */
	private Element identifyTokenType(SmartScriptToken currentToken) {

		switch (currentToken.getType()) {
		case VARIABLE:
			return new ElementVariable(currentToken.getValue().toString());

		case INTEGER:
			return new ElementConstantInteger(Integer.parseInt(currentToken.getValue().toString()));

		case DOUBLE:
			return new ElementConstantDouble(Double.parseDouble(currentToken.getValue().toString()));

		case STRING:
			return new ElementString(currentToken.getValue().toString());

		case FUNCTION:
			return new ElementFunction(currentToken.getValue().toString());

		case OPERATOR:
			return new ElementOperator(currentToken.getValue().toString());

		default:
			throw new SmartScriptParserException("Invalid SmartScriptToken type.");
		}
	}

	/**
	 * Parses the text of the document.
	 *
	 * @param token
	 *            the current token
	 */
	private void parseText(SmartScriptToken token) {
		TextNode textNode = new TextNode(token.getValue().toString());
		Node parent = (Node) stack.pop();
		parent.addChildNode(textNode);
		stack.push(parent);
	}

}
