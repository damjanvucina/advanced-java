package hr.fer.zemris.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class Vector2DTest {
	public static final double DELTA = 1e-5;

	Vector2D vector;

	@Before
	public void initialize() {
		vector = new Vector2D(1.1, 2.2);
	}

	@Test
	public void getXTest() {
		Assert.assertEquals(1.1, vector.getX(), DELTA);
	}

	@Test
	public void getYTest() {
		Assert.assertEquals(2.2, vector.getY(), DELTA);
	}

	@Test
	public void translateRegularTest() {
		vector.translate(new Vector2D(0.25, 0.5));
		Assert.assertEquals(new Vector2D(1.35, 2.7), vector);
	}

	@Test
	public void translateByNullVectorTest() {
		vector.translate(new Vector2D(0, 0));
		Assert.assertEquals(new Vector2D(1.1, 2.2), vector);
	}

	@Test(expected = NullPointerException.class)
	public void translateByNullTest() {
		vector.translate(null);
	}

	@Test
	public void translateByNegativeVectorTest() {
		vector.translate(new Vector2D(-3.3, -4.4));
		Assert.assertEquals(new Vector2D(-2.2, -2.2), vector);
	}

	@Test
	public void translatedByNullVectorTest() {
		Vector2D other = vector.translated(new Vector2D(0, 0));
		Assert.assertEquals(other, vector);
	}

	@Test(expected = NullPointerException.class)
	public void translatedByNullTest() {
		vector.translated(null);
	}

	@Test
	public void translatedByNegativeVectorTest() {
		Vector2D other = vector.translated(new Vector2D(-3.3, -4.4));
		Assert.assertEquals(new Vector2D(-2.2, -2.2), other);
	}

	@Test
	public void rotateByZeroTest() {
		vector.rotate(0);
		Assert.assertEquals(new Vector2D(1.1, 2.2), vector);
	}

	@Test
	public void rotatePositiveAngleTest() {
		vector.rotate(45);
		Assert.assertEquals(new Vector2D(-0.778174593, 2.333452378), vector);
	}
	
	@Test
	public void rotateNegativeAngleTest() {
		vector.rotate(-30);
		Assert.assertEquals(new Vector2D(2.0526279441, 1.355255888), vector);
	}
	
	@Test
	public void rotateFullCircleTest() {
		vector.rotate(360);
		Assert.assertEquals(new Vector2D(1.1, 2.2), vector);
	}

	@Test
	public void rotatedByZeroTest() {
		Vector2D other = vector.rotated(0);
		Assert.assertEquals(new Vector2D(1.1, 2.2), other);
	}

	@Test
	public void rotatedPositiveAngleTest() {//counterclockwise
		Vector2D other = vector.rotated(45);
		Assert.assertEquals(new Vector2D(-0.778174593, 2.333452378), other);
	}
	
	@Test
	public void rotatedNegativeAngleTest() {//clockwise
		Vector2D other = vector.rotated(-30);
		Assert.assertEquals(new Vector2D(2.0526279441, 1.355255888), other);
	}

	@Test
	public void rotatedFullCircleTest() {
		Vector2D other = vector.rotated(360);
		Assert.assertEquals(new Vector2D(1.1, 2.2), other);
	}

	@Test
	public void scaleByZeroTest() {
		vector.scale(0);
		Assert.assertEquals(new Vector2D(0, 0), vector);
	}

	@Test
	public void scaleByOneTest() {
		vector.scale(1);
		Assert.assertEquals(new Vector2D(1.1, 2.2), vector);
	}

	@Test
	public void scaleRegularTest() {
		vector.scale(0.731);
		Assert.assertEquals(new Vector2D(0.8041, 1.6082), vector);
	}
	
	@Test
	public void scaledByZeroTest() {
		Vector2D other = vector.scaled(0);
		Assert.assertEquals(new Vector2D(0, 0), other);
	}

	@Test
	public void scaledByOneTest() {
		Vector2D other = vector.scaled(1);
		Assert.assertEquals(new Vector2D(1.1, 2.2), other);
	}

	@Test
	public void scaledRegularTest() {
		Vector2D other = vector.scaled(0.731);
		Assert.assertEquals(new Vector2D(0.8041, 1.6082), other);
	}
	
	@Test
	public void copyRegularTest() {
		Assert.assertEquals(new Vector2D(1.1, 2.2), vector.copy());
	}

}
