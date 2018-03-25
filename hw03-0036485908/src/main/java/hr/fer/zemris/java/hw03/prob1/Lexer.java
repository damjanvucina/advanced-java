package hr.fer.zemris.java.hw03.prob1;

import hr.fer.zemris.java.hw03.prob1.LexerState;

public class Lexer {

	private char[] data;
	private Token token; // trenutni token
	private LexerState state;
	private int currentIndex; // indeks prvog neobrađenog znaka
	private int length;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text input cannot be null");
		}

		data = text.toCharArray();
		length = data.length;
		state = LexerState.BASIC;
	}

	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Lexer state cannot be set to null.");
		} else if (state != LexerState.BASIC && state != LexerState.EXTENDED) {
			throw new IllegalArgumentException("Invalid Lexer state. Valid Lexer states are BASIC and EXTENDED");
		} else {
			this.state = state;
		}
	}

	public Token nextToken() {

		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Lexer has used up all the tokens. No more tokens left.");
		}

		skipBlanks(length);

		if (currentIndex >= length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		switch (state) {
		case BASIC:
			token = basicControl();
			break;

		case EXTENDED:
			token = extendedControl();
			break;
		}

		return token;
	}

	private Token basicControl() {
		char c = data[currentIndex];

		if (Character.isDigit(c)) {
			token = processNumber();

		} else if (Character.isLetter(c) || c == '\\') {
			token = processWord();

		} else {
			token = processSymbol();
		}

		return token;
	}

	private Token extendedControl() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < length && data[currentIndex] != ' ' && data[currentIndex] != '#') {
			sb.append(data[currentIndex++]);
		}

		String output = sb.toString();
		if (output.length() > 0) {
			return new Token(TokenType.WORD, output);
		} else {
			currentIndex++;
			return new Token(TokenType.SYMBOL, Character.valueOf('#'));
		}
	}

	private Token processNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (currentIndex < length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}

		try {
			long number = Long.parseLong(sb.toString());
			return new Token(TokenType.NUMBER, number);
		} catch (NumberFormatException e) {
			throw new LexerException("Invalid input format. Number is too big.");
		}
	}

	private Token processSymbol() {
		return new Token(TokenType.SYMBOL, data[currentIndex++]);
	}

	private Token processWord() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex<length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
			if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);

			} else if (data[currentIndex] == '\\') {
				currentIndex++;

				if (currentIndex < length && (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex]))) {
					sb.append(data[currentIndex++]);

				} else {
					throw new LexerException("Invalid escape sequence");
				}
			}
		}
		return new Token(TokenType.WORD, sb.toString());
	}

	public Token getToken() {
		return token;
	}

	private void skipBlanks(int length) {
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
