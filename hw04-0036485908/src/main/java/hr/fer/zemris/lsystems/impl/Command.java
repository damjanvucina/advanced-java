package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * The interface that defines a method for executing a specific command.
 * 
 * @author Damjan Vučina
 */
public interface Command {

	/**
	 * Method that executes a command on the given context and delegates the drawing
	 * of the calculated fractal to the instance of the provided painter class.
	 *
	 * @param ctx
	 *            the context for fractal calculation
	 * @param painter
	 *            the painter that is in charge of the drawing of the calculated
	 *            fractal
	 */
	void execute(Context ctx, Painter painter);
}
