package hr.fer.zemris.java.hw03.prob1;

/**
 * The Enum which defines LexerState that is rules of character grouping.
 * 
 * @author Damjan Vučina
 */
public enum LexerState {

	/**
	 * Basic control which defines simpler rules of character grouping, that are
	 * used until lexer stumbles upon a hash (#) symbol. Grouping technique is
	 * rather intuitive. Lexer groups input words into words, symbols and numbers.
	 * Escaping numbers is allowed and those are then treated as letters meaning
	 * that they can then become parts of a word. Escape sequence '\\' is also
	 * treated as a letter.
	 */
	BASIC,
	/**
	 * * Extended control which defines more complex rules of character grouping,
	 * that are used after lexer stumbles upon a hash (#) symbol. In this regime,
	 * lexer never generates number token. Instead it concatenates all characters
	 * stumbled upon and returns them either as the word or a symbol token.
	 */
	EXTENDED
}
