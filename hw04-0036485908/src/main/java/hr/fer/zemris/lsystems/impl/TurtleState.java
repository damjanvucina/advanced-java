package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

/**
 * The Class that represents specifications of the current state of the
 * turtle."Turtle" is a synonym for an object that passes by and draw the
 * requested fractal. It contains information of its current position, direcion,
 * effective step size and drawing color.
 * 
 * @author Damjan Vučina
 */
public class TurtleState {

	/**
	 * The current position of the turtle. "Turtle" is a synonym for an object that
	 * passes by and draw the requested fractal.
	 */
	private Vector2D currentPosition;

	/**
	 * The direction of the turtle. "Turtle" is a synonym for an object that passes
	 * by and draw the requested fractal.
	 */
	private Vector2D direction;// must be unit vector

	/**
	 * The current color of the lines drawn by the turtle. "Turtle" is a synonym for
	 * an object that passes by and draw the requested fractal.
	 */
	private Color currentColor;

	/**
	 * The effective step size of the turtle - unitLength *
	 * pow(unitLengthDegreeScaler, level of production applying iteration). "Turtle"
	 * is a synonym for an object that passes by and draw the requested fractal.
	 */
	private double step;

	/**
	 * Instantiates a new turtle state. "Turtle" is a synonym for an object that
	 * passes by and draw the requested fractal.
	 *
	 * @param currentPosition
	 *            currentPosition of the turtle.
	 * @param direction
	 *            direction of the turtle.
	 * @param currentColor
	 *            the current color of the turtle.
	 * @param step
	 *            the effective step of the turtle. " The effective step size of the
	 *            turtle - unitLength * pow(unitLengthDegreeScaler, level of
	 *            production applying iteration).
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color currentColor, double step) {
		this.currentPosition = currentPosition;
		this.direction = setUnitVector(direction);
		this.currentColor = currentColor;
		this.step = step;
	}

	/**
	 * Sets the unit vector of the turtle. "Turtle" is a synonym for an object that
	 * passes by and draw the requested fractal.
	 *
	 * @param direction
	 *            the direction of the turtle
	 * @return the vector2D representation of the direction of the turtle.
	 */
	private Vector2D setUnitVector(Vector2D direction) {
		double magnitude = sqrt(pow(direction.getX(), 2) + pow(direction.getY(), 2));
		return new Vector2D(direction.getX() / magnitude, direction.getY() / magnitude);
	}

	/**
	 * Gets the current position of the turtle. "Turtle" is a synonym for an object
	 * that passes by and draw the requested fractal.
	 *
	 * @return the current position of the turtle.
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Gets the direction of the turtle. "Turtle" is a synonym for an object that
	 * passes by and draw the requested fractal.
	 *
	 * @return the direction of the turtle.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Gets the current color of fractals drawn by the turtle. "Turtle" is a synonym
	 * for an object that passes by and draw the requested fractal.
	 *
	 * @return the current color of fractals drawn by the turtle.
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * Gets the step length of the turtle. The effective step size of the turtle -
	 * unitLength * pow(unitLengthDegreeScaler, level of production applying
	 * iteration). "Turtle" is a synonym for an object that passes by and draw the
	 * requested fractal.
	 *
	 * @return the step of the turtle
	 */
	public double getStep() {
		return step;
	}

	/**
	 * Sets the current position of the turtle. "Turtle" is a synonym for an object
	 * that passes by and draw the requested fractal.
	 *
	 * @param currentPosition
	 *            the new current position of the turtle
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Sets the direction of the turtle. "Turtle" is a synonym for an object that
	 * passes by and draw the requested fractal.
	 *
	 * @param direction
	 *            the new direction of the turtle
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Sets the current color of fractals drawn by the turtle. "Turtle" is a synonym
	 * for an object that passes by and draw the requested fractal.
	 *
	 * @param currentColor
	 *            the new current color of fractals drawn by the turtle.
	 */
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * Sets the effective step of the turtle. The effective step size of the turtle
	 * - unitLength * pow(unitLengthDegreeScaler, level of production applying
	 * iteration). "Turtle" is a synonym for an object that passes by and draw the
	 * requested fractal.
	 *
	 * @param step
	 *            the new step of the turtle.
	 */
	public void setStep(double step) {
		this.step = step;
	}

	/**
	 * Returns an exact same copy of this current state of the turtle. "Turtle" is a
	 * synonym for an object that passes by and draw the requested fractal.
	 *
	 * @return the new copy of the turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition, direction, currentColor, step);
	}
}
