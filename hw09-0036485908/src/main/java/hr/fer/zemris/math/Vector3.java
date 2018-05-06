package hr.fer.zemris.math;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Objects;

public class Vector3 {

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
		double xComponent = y * other.z - z * other.y;
		double yComponent = z * other.x - x * other.z;
		double zComponent = x * other.y - y * other.x;

		return new Vector3(xComponent, yComponent, zComponent);
	}

	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	public double cosAngle(Vector3 other) {
		return dot(other) / (norm() * other.norm());
	}
	
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		sb.append(String.format("%.6f", x)).append(", ");
		sb.append(String.format("%.6f", y)).append(", ");
		sb.append(String.format("%.6f", z));
		sb.append(")");
		
		return sb.toString();
	}

	public static void main(String[] args) {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(l.norm());
		System.out.println(m);
		System.out.println(l.dot(j));
		System.out.println(i.add(new Vector3(0,1,0)).cosAngle(l));
	}
}
