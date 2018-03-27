package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantDouble extends Element {

	private double value;
	
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(getValue());
	}
}