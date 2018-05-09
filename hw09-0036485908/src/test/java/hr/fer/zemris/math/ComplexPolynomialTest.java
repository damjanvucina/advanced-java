package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexPolynomialTest {
	
	ComplexPolynomial poly1;
	ComplexPolynomial poly2;
	
	@Before
	public void setUp() {
		poly1 = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		poly2 = new ComplexPolynomial(new Complex(-1.11, 0.11), new Complex(-2.22, 0.22));
	}
	
	@Test
	public void testOrder() {
		Assert.assertEquals(1, poly1.order());
	}
	@Test
	public void testApplyPositive() {
		//System.out.println(poly1);
		Complex result = poly1.apply(new Complex(3, 3));
		Assert.assertEquals(new Complex(0, 4), result);
	}
	
	@Test
	public void testDerive() {
		System.out.println(poly1);
		System.out.println(poly1.derive());
	}

}
