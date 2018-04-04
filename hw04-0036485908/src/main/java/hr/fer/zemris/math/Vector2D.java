package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Objects;

import static java.lang.Math.abs;

/**
 * The Class that represents a classic two-dimensional vector. It provides user
 * with standards methods for managing a vector such as translating a vector by
 * another vector, rotating it by a certain angle in degrees or scaling it by a
 * certain factor. It also provides user with methods that create a new vector
 * by performing an operation (such as translation, rotation or scaling) on
 * given vector and return newly created one to the user. A Vector2D is defined
 * by its x and y coordinates. Two instances of Vector2D are considered
 * identical if the difference between their corresponding x and y properties is
 * less than 0.001.
 * 
 * @author Damjan Vučina
 */
public class Vector2D {

	/**
	 * The Constant DELTA which is used for determining wheter two instances of
	 * class Vector2D are equal. Two instances of Vector2D are considered identical
	 * if the difference between their corresponding x and y properties is less than
	 * 0.001.
	 */
	public static final double DELTA = 1e-3;

	/** The Abscissa i.e. the x coordinate of this Vector2D. */
	double x;

	/** The Ordinate i.e. the y coordinate of this Vector2D. */
	double y;

	/**
	 * Instantiates a new Vector2D with given arguments.
	 *
	 * @param x
	 *            The Abscissa i.e. the x coordinate of this Vector2D.
	 * @param y
	 *            The Ordinate i.e. the y coordinate of this Vector2D.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate of this Vector2D.
	 *
	 * @return the x coordinate of this Vector2D.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of this Vector2D..
	 *
	 * @param x
	 *            the x coordinate of this Vector2D.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of this Vector2D..
	 *
	 * @return the y coordinate of this Vector2D.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of this Vector2D..
	 *
	 * @param y
	 *            the y coordinate of this Vector2D.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Translates a Vector2D by another offset Vector2D given as method argument.
	 *
	 * @param offset
	 *            the offset Vector2D whose coordinates are used in translation
	 *            calculation
	 */
	public void translate(Vector2D offset) {
		offset = Objects.requireNonNull(offset, "Offset vector cannot be null.");

		x += offset.x;
		y += offset.y;
	}

	/**
	 * Creates a new Vector2D by translating this Vector2D by another offset
	 * Vector2D given as method argument.
	 *
	 * @param offset
	 *            the offset Vector2D whose coordinates are used in translation
	 *            calculation
	 * @return the new calculated Vector2D calculated by translation
	 */
	public Vector2D translated(Vector2D offset) {
		offset = Objects.requireNonNull(offset, "Offset vector cannot be null.");

		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Rotates this Vector2D by a specific angle in degrees specified as factor in
	 * method argument.
	 *
	 * @param angle
	 *            the angle in degrees specified as factor in method argument
	 */
	public void rotate(double angle) {
		angle = toRadians(angle);
		double copyX = x;
		double copyY = y;

		x = cos(angle) * copyX - sin(angle) * copyY;
		y = sin(angle) * copyX + cos(angle) * copyY;
	}

	/**
	 * Creates a new Vector2D by rotating this Vector2D by a specific angle in
	 * degrees specified as factor in method argument.
	 *
	 * @param angle
	 *            the angle in degrees specified as factor in method argument
	 * @return the new calculated Vector2D calculated by rotation
	 */
	public Vector2D rotated(double angle) {
		angle = toRadians(angle);

		return new Vector2D(cos(angle) * x - sin(angle) * y, sin(angle) * x + cos(angle) * y);
	}

	/**
	 * Scales this Vector2D by a specific factor given in method argument.
	 *
	 * @param scaler
	 *            the factor specified as factor in method argument used for scaling
	 *            this vector
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Creates a new Vector2D by scaling this Vector2D by a specific factor given in
	 * method argument.
	 *
	 * @param scaler
	 *            the factor specified as factor in method argument used for scaling
	 *            this vector
	 * @return the new calculated Vector2D calculated by scaling
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Returns an exact copy of this Vector2D
	 *
	 * @return the new instance of Vector2D
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "x = " + x + ", y = " + y;
	}
}
