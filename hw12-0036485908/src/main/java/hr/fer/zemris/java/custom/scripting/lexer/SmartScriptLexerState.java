package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enum SmartScriptLexerState that represents different states (i.e.
 * different set of rules) the lexer will apply when processing the text.
 * 
 * @author Damjan Vučina
 */
public enum SmartScriptLexerState {

	/**
	 * Signifies that lexer is currently processing the text of the document (i.e.
	 * outside of the tags).
	 */
	TEXT,
	/** Signifies that lexer is currently processing tags of the document. */
	TAG
}
