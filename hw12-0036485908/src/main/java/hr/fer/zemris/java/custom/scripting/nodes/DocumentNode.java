package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class DocumentNode representing an entire document. It inherits from Node
 * and as such is used for storing other classes that inhert from base Node
 * class..
 * 
 * @author Damjan Vučina
 */
public class DocumentNode extends Node {

	/**
	 * Method used for generating string representation of this document.
	 * NOTICE: this method does not use Visitor design pattern
	 */
	@Override
	public String toString() {
		return printChildrenNodes();
	}

	/**
	 * Method used for generating string representation of this document.
	 * NOTICE: this method does in fact use Visitor design pattern
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}
