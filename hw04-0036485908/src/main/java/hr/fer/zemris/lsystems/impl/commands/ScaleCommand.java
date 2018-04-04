package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * * The class in charge of scaling the unit length of the turtle's step.
 * "Turtle" is a synonym for an object that passes by and draws the requested
 * fractal.
 * 
 * @author Damjan Vučina
 */
public class ScaleCommand implements Command {

	/** The scaling factor of the unit length of the turtle's step. */
	public double factor;

	/**
	 * Instantiates a new scale command.
	 *
	 * @param factor
	 *            The scaling factor of the unit length of the turtle's step.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Method that scales the unit length of the turtle's step in the given context
	 * and delegates the drawing of the calculated fractal to the instance of the
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
		ctx.getCurrentState().setStep(ctx.getCurrentState().getStep() * factor);
	}

}
