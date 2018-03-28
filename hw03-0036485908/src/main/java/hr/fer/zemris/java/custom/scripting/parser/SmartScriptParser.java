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

public class SmartScriptParser {

	SmartScriptLexer lexer;
	ObjectStack stack;
	DocumentNode documentNode;

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

	public DocumentNode getDocumentNode() {
		return documentNode;
	}

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

		// while (token.getType() != SmartScriptTokenType.EOF) {
		// switch (token.getType()) {
		//
		// case TEXT:
		// if (token.getValue().equals("")) {
		// token = lexer.nextToken();
		// continue;
		// }
		//
		// parseText(token);
		// token = lexer.nextToken();
		// break;
		//
		// case TAG_START:
		// parseTag();
		// token = lexer.nextToken();
		// break;
		// default:
		// break;
		// }
		//
		// }

	}

	private SmartScriptToken fetchNextToken() {
		try {
			return lexer.nextToken();
		} catch (SmartScriptLexerException exc) {
			throw new SmartScriptParserException(exc.getCause());
		}
	}

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

	private void parseEnd() {
		stack.pop();

		fetchNextToken();
		confirmClosingTag("END");
	}

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

	private void confirmClosingTag(String tag) {
		if (lexer.getToken().getType() != SmartScriptTokenType.TAG_END) {
			throw new SmartScriptLexerException(tag + " tag was not properly closed.");
		}

	}

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

	private void verifyExpressionTokenType(Element expression) {
		if (expression instanceof ElementFunction || expression instanceof ElementOperator) {
			throw new SmartScriptParserException("Expression in FOR tag is an instance of unsupported class");
		}
	}

	private void verifyVariableTokenType(Element variable) {
		if (!(variable instanceof ElementVariable)) {
			throw new SmartScriptParserException(
					"First element of FOR tag must be an instance of ElementVariable class");
		}
	}

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

	private void parseText(SmartScriptToken token) {
		TextNode textNode = new TextNode(token.getValue().toString());
		Node parent = (Node) stack.pop();
		parent.addChildNode(textNode);
		stack.push(parent);
	}

}
