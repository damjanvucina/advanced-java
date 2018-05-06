package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {
	public static final double DELTA = 1e-5;
	public Vector3 vector;
	
	@Test
	public void testNormOnes() {
		vector = new Vector3(1, 1, 1);
		Assert.assertEquals(1.732050, vector.norm(), DELTA);
	}
	
	@Test
	public void testNormZeros() {
		vector = new Vector3(0, 0, 0);
		Assert.assertEquals(0, vector.norm(), DELTA);
	}
	
	@Test
	public void testNormNegativeComponents() {
		vector = new Vector3(-3.11, -2.32, -0.57);
		Assert.assertEquals(3.92166, vector.norm(), DELTA);
	}
	

}
