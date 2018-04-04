package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * The class that represents the context of the current turtle state such as
 * pushing and popping to the internally managed stack."Turtle" is a synonym for
 * an object that passes by and draws the requested fractal.
 * 
 * @author Damjan Vučina
 */
public class Context {

	/** The stack used for managing states of the turtle. */
	private ObjectStack stack;

	/**
	 * Instantiates a new context.
	 */
	public Context() {
		stack = new ObjectStack();
	}

	/**
	 * Gets the current state of the turtle.
	 *
	 * @return the current state
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}

	/**
	 * Pushes the given state to the stack.
	 *
	 * @param state
	 *            the state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Pops the state from the stack.
	 */
	public void popState() {
		stack.pop();
	}

}
