package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Class SmartScriptToken is used for encapsulating the parts of the input
 * document. Tokens are created by the lexer and sent to parser for further
 * processing
 */
public class SmartScriptToken {

	/** Enum that represents the type of the token. */
	SmartScriptTokenType type;

	/** The value. */
	Object value;

	/**
	 * Instantiates a new smart script token.
	 *
	 * @param type
	 *            Enum that represents the type of the token.
	 * @param value
	 *            the value of the token
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		if (type == null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}

		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the type of the token.
	 *
	 * @return the type of the token.
	 */
	public SmartScriptTokenType getType() {
		return type;
	}

	/**
	 * Gets the value of the token..
	 *
	 * @return the value of the token.
	 */
	public Object getValue() {
		return value;
	}
}
