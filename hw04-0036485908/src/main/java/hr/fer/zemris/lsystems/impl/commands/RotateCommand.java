package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * * The class in charge of rotating the turtle's direction, i.e. the direction
 * in which the fractal is currently being drawn. "Turtle" is a synonym for an
 * object that passes by and draws the requested fractal.
 * 
 * @author Damjan Vučina
 */
public class RotateCommand implements Command {

	/** The angle of the direcion in which the fractal is currently being drawn . */
	private double angle;

	/**
	 * Instantiates a new rotate command.
	 *
	 * @param angle
	 *            The angle of the direcion in which the fractal is currently being
	 *            drawn
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Method that updates the the direcion in which the fractal is currently being
	 * drawn in the given context and delegates the drawing of the calculated
	 * fractal to the instance of the provided painter class.
	 *
	 * @param ctx
	 *            the context for fractal calculation
	 * @param painter
	 *            the painter that is in charge of the drawing of the calculated
	 *            fractal
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D newDirection = ctx.getCurrentState().getDirection().rotated(angle);
		ctx.getCurrentState().setDirection(newDirection);
	}

}
