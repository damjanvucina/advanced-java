package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {

	private String value;

	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	private String getValue() {
		return value;
	}


	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}
