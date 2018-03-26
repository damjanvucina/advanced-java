package hr.fer.zemris.java.custom.scripting.lexer;

public class SmartScriptToken {

	SmartScriptTokenType type;
	Object value;

	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		if(type==null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		
		this.type = type;
		this.value = value;
	}

	public SmartScriptTokenType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
