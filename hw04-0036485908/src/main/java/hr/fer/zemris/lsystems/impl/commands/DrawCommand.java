package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * The class in charge of visualization of the calculated fractal.
 * 
 * @author Damjan Vučina
 */
public class DrawCommand implements Command {

	/**
	 * The effective step size of the turtle - unitLength *
	 * pow(unitLengthDegreeScaler, level of production applying iteration)."Turtle"
	 * is a synonym for an object that passes by and draws the requested fractal.
	 */
	private double step;

	/**
	 * Instantiates a new draw command.
	 *
	 * @param step
	 *            the step of the turtle
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Method that visualizes the calculated fractal in the given context and delegates the drawing
	 * of the calculated fractal to the instance of the provided painter class.
	 *
	 * @param ctx
	 *            the context for fractal calculation
	 * @param painter
	 *            the painter that is in charge of the drawing of the calculated
	 *            fractal
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		Vector2D oldPosition = currentState.getCurrentPosition();
		Vector2D direction = currentState.getDirection();
		Vector2D newPosition = oldPosition.translated(direction.scaled(step * currentState.getStep()));

		painter.drawLine(oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY(),
				currentState.getCurrentColor(), 1);

		currentState.setCurrentPosition(newPosition);
	}
}
