package hr.fer.zemris.java.hw03.prob1;

/**
 * The Enum TokenType which is used to identify different tokens after lexical
 * analysis.
 * 
 * @author Damjan Vučina
 */
public enum TokenType {

	/**
	 * The END of the processed file, generated when there are no tokens left to
	 * process
	 */
	EOF,
	/**
	 * A word, meaning a continuous set of character, or backslashed followed by a
	 * digit, or double backslash.
	 */
	WORD,
	/** A number. */
	NUMBER,
	/** A symbol. */
	SYMBOL
}
