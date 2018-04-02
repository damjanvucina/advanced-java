package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Objects;

import static java.lang.Math.abs;

public class Vector2D {
	public static final double DELTA = 1e-3;

	double x;
	double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void translate(Vector2D offset) {
		offset = Objects.requireNonNull(offset, "Offset vector cannot be null.");

		x += offset.x;
		y += offset.y;
	}

	public Vector2D translated(Vector2D offset) {
		offset = Objects.requireNonNull(offset, "Offset vector cannot be null.");

		return new Vector2D(x + offset.x, y + offset.y);
	}

	public void rotate(double angle) {
		angle = toRadians(angle);
		double copyX = x;
		double copyY = y;

		x = cos(angle) * copyX - sin(angle) * copyY;
		y = sin(angle) * copyX + cos(angle) * copyY;
	}

	public Vector2D rotated(double angle) {
		angle = toRadians(angle);
		
		return new Vector2D(cos(angle) * x - sin(angle) * y, sin(angle) * x + cos(angle) * y);
	}

	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (abs(x - other.x) > DELTA)
			// if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (abs(y - other.y) > DELTA)
			// if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public String toString() {
		return "x = " + x + ", y = " + y;
	}
}
