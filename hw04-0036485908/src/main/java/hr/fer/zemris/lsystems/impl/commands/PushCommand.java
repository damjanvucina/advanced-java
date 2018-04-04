package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * * The class in charge of pushing the turtle states to the internally managed
 * stack. "Turtle" is a synonym for an object that passes by and draws the
 * requested fractal.
 * 
 * @author Damjan Vučina
 */
public class PushCommand implements Command {

	/**
	 * Method that pushes the turtle states to the stack in the given context and
	 * delegates the drawing of the calculated fractal to the instance of the
	 * provided painter class.
	 *
	 * @param ctx
	 *            the context for fractal calculation
	 * @param painter
	 *            the painter that is in charge of the drawing of the calculated
	 *            fractal
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
