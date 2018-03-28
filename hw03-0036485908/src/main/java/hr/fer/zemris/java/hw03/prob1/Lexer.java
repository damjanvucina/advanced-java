package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

import hr.fer.zemris.java.hw03.prob1.LexerState;

/**
 * The Class that is used for the purpose of the lexical analysis. It extracts
 * the next token only when specifically asked to do so making him an instance
 * of so called "lazy" lexer. Groups tokens into words, symbols, numbers and EOF
 * analysis ending token.
 * 
 * @author Damjan Vučina
 */
public class Lexer {

	/** The data input. */
	private char[] data;

	/** The token that was last processed by this lexer. */
	private Token token;

	/** The state of the lexer that defines the rules of character grouping. */
	private LexerState state;

	/** The index of the next to be processed token */
	private int currentIndex;

	/** The length of the document provided as input for processing. */
	private int length;

	/**
	 * Instantiates a new lexer.
	 *
	 * @param text
	 *            the text of the document provided as input for processing.
	 */
	public Lexer(String text) {
		text = Objects.requireNonNull(text, "Text input cannot be null");

		data = text.toCharArray();
		length = data.length;
		state = LexerState.BASIC;
	}

	/**
	 * Sets the state of the lexer that defines the rules of character grouping. .
	 *
	 * @param state
	 *            the state of the lexer that defines the rules of character
	 *            grouping.
	 */
	public void setState(LexerState state) {
		state = Objects.requireNonNull(state, "Lexer state cannot be set to null.");
		
		if (state != LexerState.BASIC && state != LexerState.EXTENDED) {
			throw new IllegalArgumentException("Invalid Lexer state. Valid Lexer states are BASIC and EXTENDED");
		} else {
			this.state = state;
		}
	}

	/**
	 * Method invoked for the purpose of acquiring next token. Delegates its work to
	 * other methods.
	 * 
	 * @throws LexerException
	 *             in case of invalid character grouping stumbled upon by this lexer
	 *
	 * @return the next token
	 */
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

	/**
	 * Basic control which defines simpler rules of character grouping, that are
	 * used until lexer stumbles upon a hash (#) symbol. Grouping technique is
	 * rather intuitive. Lexer groups input words into words, symbols and numbers.
	 * Escaping numbers is allowed and those are then treated as letters meaning
	 * that they can then become parts of a word. Escape sequence '\\' is also
	 * treated as a letter.
	 *
	 * @return the new token
	 */
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

	/**
	 * Extended control which defines more complex rules of character grouping, that
	 * are used after lexer stumbles upon a hash (#) symbol. In this regime, lexer
	 * never generates number token. Instead it concatenates all characters stumbled
	 * upon and returns them either as the word or a symbol token.
	 *
	 * @return the token
	 */
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

	/**
	 * Processes the number stumbled upon by lexer.
	 *
	 * @return the next token
	 */
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

	/**
	 * Processes the symbol stumbled upon by lexer..
	 *
	 * @return the next token
	 */
	private Token processSymbol() {
		return new Token(TokenType.SYMBOL, data[currentIndex++]);
	}

	/**
	 * Processes the word stumbled upon by lexer..
	 *
	 * @return the next token
	 */
	private Token processWord() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
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

	/**
	 * Gets the last processed token.
	 *
	 * @return the last processed token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Skips blanks.
	 *
	 * @param length
	 *            the length of the document
	 */
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
