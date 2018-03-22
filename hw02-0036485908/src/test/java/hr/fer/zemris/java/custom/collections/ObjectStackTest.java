package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ObjectStackTest {

	public ObjectStack stack;

	public ObjectStack fillStack() {
		stack.push("First");
		stack.push("Second");
		stack.push("Third");
		stack.push("Fourth");

		return stack;
	}

	@Before
	public void initialize() {
		stack = new ObjectStack();
	}

	@Test
	public void testIsEmpty() {
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testClearRegular() {
		stack = fillStack();
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testClearEmptyStack() {
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test(expected = EmptyStackException.class)
	public void testPeekEmptyStack() {
		stack.peek();
	}

	@Test(expected = EmptyStackException.class)
	public void testPopEmptyStack() {
		stack.pop();
	}

	@Test(expected = NullPointerException.class)
	public void testPushNull() {
		stack.push(null);
	}
	
	@Test
	public void testSizeRegular() {
		stack=fillStack();
		assertEquals(4, stack.size());
	}
	
	@Test
	public void testPushAndPopRegular() {
		stack=fillStack();
		stack.push("Fifth");
		assertEquals("Fifth", stack.pop());
	}
}
