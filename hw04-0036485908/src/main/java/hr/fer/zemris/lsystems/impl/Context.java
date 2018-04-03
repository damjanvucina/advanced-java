package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class Context {

	private ObjectStack stack;

	public Context() {
		stack = new ObjectStack();
	}

	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}

	public void pushState(TurtleState state) {
		stack.push(state);
	}

	public void popState() {
		stack.pop();
	}

}
