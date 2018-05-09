package hr.fer.zemris.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ComplexRootedPolynomialTest {
	
	ComplexRootedPolynomial poly1;
	ComplexRootedPolynomial poly2;
	
	@Before
	public void setUp() {
		poly1 = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2));
		poly1 = new ComplexRootedPolynomial(new Complex(-1.11, 0.11), new Complex(-2.22, 0.22));
		
	}
	
	@Test
	public void testApplyPositive() {
		Complex result = poly1.apply(new Complex(3, 3));
		Assert.assertEquals(new Complex(0, 4), result);
	}
	
	public void testApplyNegative() {
		Complex result = poly1.apply(new Complex(-3.33, 0.33));
		Assert.assertEquals(new Complex(2.44, -0.4884), result);
	}

}
