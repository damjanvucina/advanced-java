package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {

	private String name;

	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	private String getName() {
		return name;
	}

	@Override
	public String asText() {
		return String.valueOf(getName());
	}
}
