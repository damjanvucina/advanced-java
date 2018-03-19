package hr.fer.zemris.java.custom.collections;

public class ObjectStack {

	public static final int STACK_TOP = 0;

	private ArrayIndexedCollection adaptor;

	public ObjectStack() {
		adaptor = new ArrayIndexedCollection();
	}

	public boolean isEmpty() {
		return adaptor.isEmpty();
	}

	public int size() {
		return adaptor.size();
	}

	public void push(Object value) {
		adaptor.insert(value, STACK_TOP + 1);
	}

	public Object pop() {
		if (isEmpty()) {
			throw new EmptyStackException("You cannot pop element from empty stack");
		}
		Object value = peek();
		adaptor.remove(STACK_TOP);

		return value;
	}

	public Object peek() {
		return adaptor.get(STACK_TOP);
	}
	
	public void clear() {
		adaptor.clear();
	}

}
