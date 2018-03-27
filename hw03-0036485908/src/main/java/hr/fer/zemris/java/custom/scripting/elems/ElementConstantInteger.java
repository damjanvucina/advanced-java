package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {

	private int value;
	
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	private int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
