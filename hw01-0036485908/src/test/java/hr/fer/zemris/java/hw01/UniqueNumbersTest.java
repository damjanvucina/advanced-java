package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	public static TreeNode constructBinaryTree() {
		TreeNode glava = null;

		glava = UniqueNumbers.addNode(glava, 0);
		glava = UniqueNumbers.addNode(glava, 33);
		glava = UniqueNumbers.addNode(glava, 12);
		glava = UniqueNumbers.addNode(glava, -12);
		glava = UniqueNumbers.addNode(glava, 12);
		glava = UniqueNumbers.addNode(glava, 33);

		return glava;
	}
	
	@Test
	public void addNodeNegative() {
		TreeNode glava = constructBinaryTree();
		glava = UniqueNumbers.addNode(glava, -27);
		Assert.assertTrue(UniqueNumbers.containsValue(glava, -27));
	}

	@Test
	public void treeSizeEmpty() {
		Assert.assertEquals(0, UniqueNumbers.treeSize(null));
	}
	
	@Test
	public void treeSizeDuplicates() {
		Assert.assertEquals(4, UniqueNumbers.treeSize(constructBinaryTree()));
	}
	
	@Test
	public void containsValueEmptyTree() {
		Assert.assertFalse(UniqueNumbers.containsValue(null, 1));
	}
	
	@Test
	public void containsValueNegative() {
		Assert.assertTrue(UniqueNumbers.containsValue(constructBinaryTree(), -12));
	}
	
}
