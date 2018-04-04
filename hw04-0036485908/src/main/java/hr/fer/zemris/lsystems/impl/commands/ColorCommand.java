package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * The class in charge of updating the color of the lines drawn by the turtle.
 * "Turtle" is a synonym for an object that passes by and draws the requested
 * fractal.
 * 
 * @author Damjan Vučina
 */
public class ColorCommand implements Command {

	/** The color of the lines to be drawn by the turtle. */
	Color color;

	/**
	 * Instantiates a new color command.
	 *
	 * @param color
	 *            the color of the lines to be drawn by the turtle.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Method that updates the color in the given context and delegates the drawing
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
		ctx.getCurrentState().setCurrentColor(color);
	}

}
