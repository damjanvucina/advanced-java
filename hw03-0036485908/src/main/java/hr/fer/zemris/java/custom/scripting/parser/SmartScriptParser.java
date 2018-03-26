package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.Token;

public class SmartScriptParser {

	SmartScriptLexer lexer;
	ObjectStack stack;
	DocumentNode documentNode;

	public SmartScriptParser(String document) {
		lexer = new SmartScriptLexer(document);
		stack = new ObjectStack();
		documentNode = new DocumentNode();

		stack.push(documentNode);

		parse();
	}

	public void parse() {
		SmartScriptToken token;

		while (true) {

			token = lexer.nextToken();

			switch (token.getType()) {
			case TEXT:
				parseText(token);
				break;

			case TAG_START:
				continue;

			case TAG_END:
				continue;

			case TAG:
				parseTag(token);
				break;

			default:
				break;
			}

		}

	}

	private void parseTag(SmartScriptToken token) {

		switch (token.getValue().toString()) {

		case "FOR":
			parseFor();
			break;

		default: // any valid variable name
			break;
		}
	}

	private void parseFor() {
		ArrayIndexedCollection forTokens = new ArrayIndexedCollection();
		SmartScriptToken currentToken = lexer.nextToken();

		while (currentToken.getType() != SmartScriptTokenType.TAG_END) {
			forTokens.add(currentToken);
			currentToken = lexer.nextToken();
		}

		if (forTokens.size() > 4) {
			throw new SmartScriptParserException(
					"Invalid number of arguments within FOR tag, must be either 3 or 4, was: " + forTokens.size());
		}

	}

	private void parseText(SmartScriptToken token) {
		TextNode textNode = new TextNode(token.getValue().toString());
		Node parent = (Node) stack.peek();
		parent.addChildNode(textNode);
	}

}
