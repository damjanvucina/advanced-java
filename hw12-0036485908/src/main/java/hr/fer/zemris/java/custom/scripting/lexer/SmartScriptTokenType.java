package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enum SmartScriptTokenType that represents different tags and symbols that
 * the lexer may come across when processing the document.
 * 
 * @a
 * 
 */
public enum SmartScriptTokenType {

	/** Signifies that the lexer started to process a tag. */
	TAG_START,
	/** Signifies that the lexer ended processing a tag. */
	TAG_END,
	/**
	 * Specific tag that the lexer is processing a tag of some sort (i.e. either FOR
	 * or ECHO)
	 */
	TAG,
	/** Signifies that the lexer is processing a variable. */
	VARIABLE,
	/** Signifies that the lexer is processing a function. */
	FUNCTION,
	/** Signifies that the lexer is processing an operator. */
	OPERATOR,
	/** Signifies that the lexer is processing a text within the FOR tag. */
	TEXT,
	/** Signifies that the lexer is processing the document. */
	EOF,
	/** Signifies that the lexer is processing an integer. */
	INTEGER,
	/** Signifies that the lexer ended processing a double. */
	DOUBLE,
	/** Signifies that the lexer ended processing a string. */
	STRING;
}
