package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Interface INodeVisitor.
 * 
 * @author Damjan Vučina
 */
public interface INodeVisitor {

	/**
	 * Visit text node.
	 *
	 * @param node the node
	 */
	void visitTextNode(TextNode node);

	/**
	 * Visit for loop node.
	 *
	 * @param node the node
	 */
	void visitForLoopNode(ForLoopNode node);

	/**
	 * Visit echo node.
	 *
	 * @param node the node
	 */
	void visitEchoNode(EchoNode node);

	/**
	 * Visit document node.
	 *
	 * @param node the node
	 */
	void visitDocumentNode(DocumentNode node);
}
