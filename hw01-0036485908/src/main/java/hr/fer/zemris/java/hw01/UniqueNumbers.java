package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The Class that represents a binary tree for the purpose of storing TreeNode
 * elements.
 *
 * @author Damjan Vučina
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * The Class that represents a tree's node.
	 */
	class TreeNode {

		/** The left child of the current node */
		TreeNode left;

		/** The right child of the current node */
		TreeNode right;

		/** The value of the current node */
		int value;
	}
	
	/**
	 * The method invoked when the program is run.
	 *
	 * @param args
	 *            Command Line arguments. They are not used here.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;

		while (true) {
			System.out.print("Unesite broj > ");

			if (sc.hasNextInt()) {
				int input = sc.nextInt();
				if (!containsValue(glava, input)) {
					glava = addNode(glava, input);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj već postoji. Preskačem.");
					continue;

				}

			} else if (sc.hasNext("kraj")) {
				System.out.print("Ispis od najmanjeg: ");
				printAscending(glava);
				System.out.println();
				System.out.print("Ispis od najvećeg: ");
				printDescending(glava);
				break;

			} else {
				System.out.println("'" + sc.next() + "' nije cijeli broj.");
			}
		}
		sc.close();
	}

	/**
	 * Adds the node to the binary tree represented by its root.
	 * Complexity: O(log(n))
	 *
	 * @param root
	 *            The root of the tree
	 * @param value
	 *            The value of the new node that is about to be created and added to
	 *            the tree.
	 * @return Newly created tree node.
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if (root == null) {
			root = new UniqueNumbers().new TreeNode();
			root.value = value;
		} else if (value < root.value) {
			root.left = addNode(root.left, value);
		} else if (value > root.value) {
			root.right = addNode(root.right, value);
		}
		return root;
	}

	/**
	 * Calculates the size of the tree represented by its root.
	 *
	 * @param root
	 *            The root of the tree.
	 * @return Number of the nodes in the tree
	 */
	public static int treeSize(TreeNode root) {
		if (root == null) {
			return 0;
		} else {
			return 1 + treeSize(root.left) + treeSize(root.right);
		}
	}

	/**
	 * Checks if the tree represented by its root contains a node with specified
	 * value.
	 *
	 * @param root
	 *            The root of the tree.
	 * @param value
	 *            The value of the node
	 * @return true, if the tree contains a node with specified value.
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if (root == null) {
			return false;
		} else if (value < root.value) {
			return containsValue(root.left, value);
		} else if (value > root.value) {
			return containsValue(root.right, value);
		} else {
			return true;
		}
	}

	

	/**
	 * Prints the nodes of the tree in descending order of the nodes' values.
	 *
	 * @param glava
	 *            The root of the tree.
	 */
	public static void printDescending(TreeNode glava) {
		if (glava != null) {
			printDescending(glava.right);
			System.out.print(glava.value + " ");
			printDescending(glava.left);
		}
	}

	/**
	 * Prints the nodes of the tree in ascending order of the nodes' values.
	 *
	 * @param glava
	 *            The root of the tree.
	 */
	public static void printAscending(TreeNode glava) {
		if (glava != null) {
			printAscending(glava.left);
			System.out.print(glava.value + " ");
			printAscending(glava.right);
		}
	}

}
