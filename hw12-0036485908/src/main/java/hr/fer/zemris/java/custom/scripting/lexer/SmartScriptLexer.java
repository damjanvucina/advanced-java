package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Class SmartScriptLexer represents an object that screens the document
 * provided in constructor, processes it according to the specified token types
 * and different set of rules for character grouping. It breaks the input into
 * different tokens and forwards them to the parser for further processing.
 * 
 * @author Damjan Vučina
 */
public class SmartScriptLexer {

	/** The data that is to be processed by the lexer. */
	private char[] data;

	/** The token that has last been processed. */
	private SmartScriptToken token;

	/** The current state of the lexer (i.e. either TEXT or TAG). */
	private SmartScriptLexerState state;

	/** The index of the character that is to be processed by the lexer. */
	private int currentIndex;

	/** The length of the document. */
	private int length;

	/**
	 * Instantiates a new smart script lexer.
	 *
	 * @param text
	 *            the text that is to be processed by the lexer
	 * @throws SmartScriptLexerException
	 *             if data that is supposed to be processed equal to null
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("Input text cannot be null");
		}

		data = text.toCharArray();
		length = data.length;
		state = SmartScriptLexerState.TEXT;
	}

	/**
	 * The method that is invoked by the parser whenever a new token needs to be
	 * processed. It delegates its work to other methods and returns a next token
	 * they provide.
	 *
	 * @return a newly processed SmartScriptToken
	 * @throws SmartScriptLexerException
	 *             if Lexer has used up all the tokens or invalid tagging occured.
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("Lexer has used up all the tokens. No more tokens left.");
		}

		if (currentIndex >= length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}

		switch (state) {
		case TEXT:
			token = textControl();
			break;

		case TAG:
			token = tagControl();
			break;
		}

		return token;
	}

	/**
	 * Method invoked by the lexer whenever a starting tag has occured and the state
	 * of the lexer has been changed to TAG meaning different set of rules are used.
	 * It delegates its work to other methods depending on the character grouping
	 * and tagging.
	 *
	 * @return SmartScriptToken
	 * @throws SmartScriptLexerException
	 *             if invalid tagging occured which is not in conformance to the
	 *             defined syntax
	 */
	private SmartScriptToken tagControl() {

		if (data[currentIndex] == '{') {
			currentIndex++;

			if (data[currentIndex] != '$') {
				throw new SmartScriptLexerException("Invalid starting tag");
			} else {
				currentIndex++;// {$
				return new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$");
			}
		}

		skipBlanks();

		if (data[currentIndex] == '=') {
			currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.TAG, "=");

		} else if (isForTag()) {
			currentIndex += 3;
			return new SmartScriptToken(SmartScriptTokenType.TAG, "FOR");

		} else if (isEndTag()) {
			currentIndex += 3;
			return new SmartScriptToken(SmartScriptTokenType.TAG, "END");

		} else if (isLetter()) {
			return processVariableName(SmartScriptTokenType.VARIABLE);
		}

		else if (isFunction()) {
			currentIndex++;
			return processVariableName(SmartScriptTokenType.FUNCTION);

		} else if (isOperator()) {
			return new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex++]);

		} else if (isNumber()) {
			return processNumber();

		} else if (isString()) {
			currentIndex++;
			return processString();
		}

		else if (data[currentIndex] == '$') {
			currentIndex++;

			if (data[currentIndex] != '}') {
				throw new SmartScriptLexerException("Invalid closing tag.");
			} else {
				currentIndex++;// $}
				setState(SmartScriptLexerState.TEXT);
				return new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}");
			}

		}
		throw new SmartScriptLexerException("Invalid syntax");
	}

	/**
	 * Checks if the next tag to be processed is the END tag.
	 *
	 * @return true, if is end tag
	 */
	private boolean isEndTag() {
		if (currentIndex + 3 >= length) {
			return false;
		}

		return String.valueOf(data).substring(currentIndex, currentIndex + 3).toUpperCase().equals("END");
	}

	/**
	 * Checks if the next tag to be processed is the FOR tag.
	 *
	 * @return true, if is for tag
	 */
	private boolean isForTag() {
		if (currentIndex + 3 >= length) {
			return false;
		}

		return String.valueOf(data).substring(currentIndex, currentIndex + 3).toUpperCase().equals("FOR");
	}

	/**
	 * Method that is invoked whenever lexer comes across a String variable when
	 * processing contents of the different TAGs. Escaping is permitted in due form:
	 * \\ sequence treat as a single string character \ \" treat as a single string
	 * character " (and not the end of the string) \n, \r and \t have its usual
	 * meaning
	 *
	 * @return the smart script token
	 * @throws SmartScriptLexerException
	 *             if invalid escape sequence occured or if string was not properly
	 *             closed.
	 */
	private SmartScriptToken processString() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < length && data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {

				char nextToken = data[currentIndex + 1];

				if (isRegularEscapeSequence(nextToken)) {
					sb.append(data[currentIndex++]);
					sb.append(data[currentIndex++]);

				} else if (isQuoteOrSlashEscapeSequence(nextToken)) {
					currentIndex++;
					sb.append(data[currentIndex++]);

				} else {
					throw new SmartScriptLexerException("Invalid escape sequence");
				}
			}
			sb.append(data[currentIndex++]);
		}

		if (data[currentIndex] == '\"') {
			currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString().replace("\\n", "\n").replace("\\r", "\r"));
		} else {
			throw new SmartScriptLexerException("String was not properly closed.");
		}

	}

	/**
	 * Checks if the next escape sequence is either a slash ot quote escape sequence
	 * 
	 * @param nextToken
	 *            the next token
	 * @return true, if is quote or slash escape sequence
	 */
	private boolean isQuoteOrSlashEscapeSequence(char nextToken) {
		return (nextToken == '"' || nextToken == '\\');
	}

	/**
	 * Checks if the next escape sequence is a regular escape sequence that denotes
	 * occurence of one or more whitespaces (i.e. \r or \n or \t).
	 *
	 * @param nextToken
	 *            the next token
	 * @return true, if is regular escape sequence
	 */
	private boolean isRegularEscapeSequence(char nextToken) {
		return (nextToken == 'r' || nextToken == 'n' || nextToken == 't');
	}

	/**
	 * Checks if next token is string.
	 *
	 * @return true, if next token is string
	 */
	private boolean isString() {
		return (data[currentIndex] == '\"');
	}

	/**
	 * Checks if next token is letter.
	 *
	 * @return true, if next token is letter
	 */
	private boolean isLetter() {
		return (Character.isLetter(data[currentIndex]));
	}

	/**
	 * Checks if next token is function.
	 *
	 * @return true, if next token is function
	 */
	private boolean isFunction() {
		return (data[currentIndex] == '@' && Character.isLetter(data[currentIndex + 1]));
	}

	/**
	 * Processes the number depending on its type as both integer and double values
	 * are supported.
	 *
	 * @return the smart script token
	 */
	private SmartScriptToken processNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}

		if (data[currentIndex] == '.') {
			sb.append(data[currentIndex++]);

			while (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
			return new SmartScriptToken(SmartScriptTokenType.DOUBLE, sb.toString());
		}
		return new SmartScriptToken(SmartScriptTokenType.INTEGER, sb.toString());
	}

	/**
	 * Checks if next token is number.
	 *
	 * @return true, if next token is number
	 */
	private boolean isNumber() {
		return (Character.isDigit(data[currentIndex])
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])));
	}

	/**
	 * Checks if next token is operator. Valid operators are + (plus), - (minus), *
	 * (multiplication), / (division), ^ (power).
	 *
	 * @return true, if next token is operator
	 */
	private boolean isOperator() {
		return (data[currentIndex] == '+'
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]) == false)
				|| data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^');
	}

	/**
	 * Processes variable name. Valid variable name starts by letter and after
	 * follows zero or more letters, digits or underscores. If name is not valid, it
	 * is invalid. This variable names are valid: A7_bb, counter, tmp_34; these are
	 * not: _a21, 32, 3s_ee etc.
	 *
	 * @param variable
	 *            the variable
	 * @return the smart script token
	 */
	private SmartScriptToken processVariableName(SmartScriptTokenType variable) {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (isValidVariableName()) {
			sb.append(data[currentIndex++]);
		}

		return new SmartScriptToken(variable, sb.toString());
	}

	/**
	 * Checks if is valid variable name. Valid variable name starts by letter and
	 * after follows zero or more letters, digits or underscores. If name is not
	 * valid, it is invalid. This variable names are valid: A7_bb, counter, tmp_34;
	 * these are not: _a21, 32, 3s_ee etc.
	 *
	 * @return true, if is valid variable name
	 */
	public boolean isValidVariableName() {
		return (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_');
	}

	/**
	 * Method that is invoked when lexer comes across text that is not surrounded by
	 * tag starting brackets. It allows escaping in form of translating '\\' to '\'
	 * and '\{' to '{'. Other escape sequences are invalid
	 *
	 * @return the smart script token
	 * 
	 */
	private SmartScriptToken textControl() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < length && data[currentIndex] != '{') {
			if (data[currentIndex] == '\\') {
				currentIndex++;

				if (currentIndex < length && (data[currentIndex] == '\\' || data[currentIndex] == '{')) {
					sb.append(data[currentIndex++]);

				} else {
					throw new SmartScriptLexerException("Invalid escape sequence");
				}

			} else {
				sb.append(data[currentIndex++]);
			}
		}

		if (currentIndex < length && data[currentIndex] == '{') {// don't consume it yet, tagControl method will
			setState(SmartScriptLexerState.TAG);
		}
		return new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());
	}

	/**
	 * Sets the state of the lexer. Valid states are either TEXT or TAG.
	 *
	 * @param state
	 *            the new state
	 * @throws SmartScriptLexerException
	 *             if lexer state is to be set to null or to unkown state.
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new SmartScriptLexerException("Lexer state cannot be set to null.");
		} else if (state != SmartScriptLexerState.TEXT && state != SmartScriptLexerState.TAG) {
			throw new SmartScriptLexerException("Invalid Lexer state. Valid Lexer states are TEXT and TAG");
		} else {
			this.state = state;
		}
	}

	/**
	 * Gets the last processed token.
	 *
	 * @return the token
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Skips blanks.
	 */
	private void skipBlanks() {
		while (currentIndex < length) {
			char c = data[currentIndex];

			if (c == '\r' || c == '\t' || c == '\n' || c == ' ') {
				currentIndex++;
			} else {
				break;
			}
		}
	}

}
