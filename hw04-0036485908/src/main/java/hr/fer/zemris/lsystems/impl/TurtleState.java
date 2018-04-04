package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class TurtleState {

	private Vector2D currentPosition;
	private Vector2D direction;// must be unit vector
	private Color currentColor;
	private double step;

	public TurtleState(Vector2D currentPosition, Vector2D direction, Color currentColor, double step) {
		this.currentPosition = currentPosition;
		this.direction = setUnitVector(direction);
		this.currentColor = currentColor;
		this.step = step;
	}

	private Vector2D setUnitVector(Vector2D direction) {
		double magnitude = sqrt(pow(direction.getX(), 2) + pow(direction.getY(), 2));
		return new Vector2D(direction.getX()/magnitude, direction.getY()/magnitude);
	}

	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public double getStep() {
		return step;
	}

	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public TurtleState copy() {
		return new TurtleState(currentPosition, direction, currentColor, step);
	}
}
