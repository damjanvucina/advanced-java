package hr.fer.zemris.math;

import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import java.util.Objects;

public class Vector3 {
	public static final double DELTA = 1e-6;

	private double x;
	private double y;
	private double z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double norm() {
		return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
	}

	public Vector3 normalized() {
		double norm = norm();
		if (norm == 0) {
			throw new IllegalArgumentException("Cannot normalize nul-vector.");
		}
		return new Vector3(x / norm, y / norm, z / norm);
	}

	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Other vector cannot be null.");

		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Other vector cannot be null.");

		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Other vector cannot be null.");

		return x * other.x + y * other.y + z * other.z;
	}

	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Other vector cannot be null.");

		double xComponent = y * other.z - z * other.y;
		double yComponent = z * other.x - x * other.z;
		double zComponent = x * other.y - y * other.x;

		return new Vector3(xComponent, yComponent, zComponent);
	}

	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	public double cosAngle(Vector3 other) {
		double thisNorm = norm();
		double otherNorm = other.norm();
		if(thisNorm == 0 || other.norm()==0) {
			throw new IllegalArgumentException("Cannot calculate angle");
		}
		
		return dot(other) / (thisNorm * otherNorm);
	}

	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(String.format("%.6f", x)).append(", ");
		sb.append(String.format("%.6f", y)).append(", ");
		sb.append(String.format("%.6f", z));
		sb.append(")");

		return sb.toString();
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
		temp = Double.doubleToLongBits(z);
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
		Vector3 other = (Vector3) obj;
		if (abs(x - other.x) > DELTA)
			// if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (abs(y - other.y) > DELTA)
			// if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (abs(z - other.z) > DELTA)
			// if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

}
