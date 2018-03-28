package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class DocumentNode representing an entire document. It inherits from Node
 * and as such is used for storing other classes that inhert from base Node
 * class..
 * 
 * @author Damjan Vučina
 */
public class DocumentNode extends Node {

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return printChildrenNodes();
	}

}
