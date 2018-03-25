package hr.fer.zemris.java.hw03.prob1;

public class Token {

	TokenType type;
	Object value;

	public Token(TokenType type, Object value) {
		if(type==null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

}
