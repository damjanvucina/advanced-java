package hr.fer.zemris.java.hw03.prob1;

public class Lexer {

	private char[] data;
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private int length;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text input cannot be null");
		}

		data = text.toCharArray();
		length = data.length;
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

		while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);

			} else if (data[currentIndex] == '\\') {
				currentIndex++;

				if (currentIndex < length && (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex]))) {
					sb.append(data[currentIndex]);
					currentIndex++;

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
