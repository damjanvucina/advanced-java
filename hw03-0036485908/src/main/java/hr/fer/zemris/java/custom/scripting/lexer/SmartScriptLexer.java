package hr.fer.zemris.java.custom.scripting.lexer;

public class SmartScriptLexer {

	private char[] data;
	private SmartScriptToken token; // trenutni token
	private SmartScriptLexerState state;
	private int currentIndex; // indeks prvog neobrađenog znaka
	private int length;

	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("Input text cannot be null");
		}

		data = text.toCharArray();
		length = data.length;
		state = SmartScriptLexerState.TEXT;
	}

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

	private boolean isEndTag() {
		if (currentIndex + 3 >= length) {
			return false;
		}

		return String.valueOf(data).substring(currentIndex, currentIndex + 3).toUpperCase().equals("END");
	}

	private boolean isForTag() {
		if (currentIndex + 3 >= length) {
			return false;
		}

		return String.valueOf(data).substring(currentIndex, currentIndex + 3).toUpperCase().equals("FOR");
	}

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
			return new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString());
		} else {
			throw new SmartScriptLexerException("String was not properly closed.");
		}

	}

	private boolean isQuoteOrSlashEscapeSequence(char nextToken) {
		return (nextToken == '"' || nextToken == '\\');
	}

	private boolean isRegularEscapeSequence(char nextToken) {
		return (nextToken == 'r' || nextToken == 'n' || nextToken == 't');
	}

	private boolean isString() {
		return (data[currentIndex] == '\"');
	}

	private boolean isLetter() {
		return (Character.isLetter(data[currentIndex]));
	}

	private boolean isFunction() {
		return (data[currentIndex] == '@' && Character.isLetter(data[currentIndex+1]));
	}

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

	private boolean isNumber() {
		return (Character.isDigit(data[currentIndex])
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])));
	}

	private boolean isOperator() {
		return (data[currentIndex] == '+'
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]) == false)
				|| data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^');
	}

	private SmartScriptToken processVariableName(SmartScriptTokenType variable) {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (isValidVariableName()) {
			sb.append(data[currentIndex++]);
		}

		return new SmartScriptToken(variable, sb.toString());
	}

	public boolean isValidVariableName() {
		return (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_');
	}

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

	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new SmartScriptLexerException("Lexer state cannot be set to null.");
		} else if (state != SmartScriptLexerState.TEXT && state != SmartScriptLexerState.TAG) {
			throw new IllegalArgumentException("Invalid Lexer state. Valid Lexer states are TEXT and TAG");
		} else {
			this.state = state;
		}
	}

	public SmartScriptToken getToken() {
		return token;
	}

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
