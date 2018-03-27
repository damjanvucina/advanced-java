package hr.fer.zemris.java.custom.scripting.elems;

public class ElementOperator extends Element {

	private String symbol;

	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	private String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return String.valueOf(getSymbol());
	}
}
