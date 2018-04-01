package hr.fer.zemris.java.custom.collections;

/**
 * The class that represents a stack-like implementation. It serves as the
 * Adaptor in the Adapter Pattern while the ArrayIndexedCollection is the
 * Adaptee. It provides all of the classic methods for stack transformation such
 * as push, pop, peek and so on.
 * 
 * @author Damjan Vučina
 * @version 1.0
 */
public class ObjectStack {

	/**
	 * The reference to the ArrayIndexedCollection that is used to store the
	 * elements of the stack.
	 */
	private ArrayIndexedCollection adaptor;

	/**
	 * Instantiates a new object stack implemented using ArrayIndexedCollection as
	 * the Adaptee in the well-known Adaptor Pattern.
	 */
	public ObjectStack() {
		adaptor = new ArrayIndexedCollection();
	}

	/**
	 * Checks if the stack contains no elements
	 *
	 * @return true, if the stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return adaptor.isEmpty();
	}

	/**
	 * Counts the number of elements stored in the stack.
	 * 
	 * @return number of elements stored in the stack.
	 */
	public int size() {
		return adaptor.size();
	}

	/**
	 * Inserts the object provided as the argument to the top of the stack. .
	 *
	 * @param value
	 *            object which is to be added to the stack
	 * @throws NullPointerException
	 *             if the user tries to add null value to the stack
	 */
	public void push(Object value) {
		adaptor.insert(value, size());
	}

	/**
	 * Method that removes last value pushed on stack from stack and returns it.
	 *
	 * @return the object that was last pushed to the stack.
	 * @throws EmptyStackException
	 *             if user tries to pop an element from an empty stack.
	 */
	public Object pop() {
		Object value = peek();
		adaptor.remove(size()-1);

		return value;
	}

	/**
	 * returns last element placed on stack but does not delete it from stack.
	 *
	 * @return the object last pushed on the stack
	 * @throws EmptyStackException
	 *             if user tries to pop an element from an empty stack.
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("You cannot pop element from empty stack");
		}
		return adaptor.get(size()-1);
	}

	/**
	 * Clears the stack by deleting all of its elements, result is an empty stack.
	 */
	public void clear() {
		adaptor.clear();
	}

}
