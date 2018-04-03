package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

public class TurtleState {

	private Vector2D currentPosition;
	private Vector2D direction;// must be unit vector
	private Color currentColor;
	private double step;

	public TurtleState(Vector2D currentPosition, Vector2D direction, Color currentColor, double step) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.currentColor = currentColor;
		this.step = step;
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
