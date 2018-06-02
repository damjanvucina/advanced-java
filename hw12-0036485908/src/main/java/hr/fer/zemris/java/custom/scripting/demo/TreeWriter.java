package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * The class that represents a program responsible for printing the tree
 * represented by the DocumentNode. Visitor design pattern is in use.
 * 
 * @author Damjan Vučina
 */
public class TreeWriter {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid input. Expected single file path argument.");
			return;
		}

		Path path = Paths.get(args[0]);

		if (Files.notExists(path)) {
			System.out.println("File does not exist.");
			return;
		}

		if (!Files.isReadable(path)) {
			System.out.println("File is not readable.");
			return;
		}

		if (!Files.isRegularFile(path)) {
			System.out.println("File must be a regular file, not a directory.");
			return;
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(path), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);

	}

	/**
	 * The class that is responisble for providing an implementation of interface
	 * that defines the printing of the nodes.
	 */
	static class WriterVisitor implements INodeVisitor {

		/**
		 * Prints TextNode.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		/**
		 * Prints ForLoopNode.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
		}

		/**
		 * Prints EchoNode.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		/**
		 * Prints DocumentNode.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				Node currentNode = node.getChild(i);
				currentNode.accept(this);
			}

		}
	}

}
