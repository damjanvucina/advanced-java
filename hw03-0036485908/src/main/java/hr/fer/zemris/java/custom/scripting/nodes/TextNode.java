package hr.fer.zemris.java.custom.scripting.nodes;

public class TextNode extends Node {

	private String text;
	
	public TextNode(String text) {
		//super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}


}
