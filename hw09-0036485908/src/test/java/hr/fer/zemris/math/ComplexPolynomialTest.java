package hr.fer.zemris.math;

import org.junit.Test;

public class ComplexPolynomialTest {
	
	ComplexPolynomial poly1;
	ComplexPolynomial poly2;
	ComplexPolynomial poly3;
	
	@Test
	public void multiplicationTestRegular() {
		poly1 = new ComplexPolynomial(new Complex[] {new Complex(1, 0),
													 new Complex(2, 0),
													 new Complex(-3, 0)});
		
		poly2 = new ComplexPolynomial(new Complex[] {new Complex(1, 0),
				 								 	 new Complex(7, 0),
				 									 new Complex(10, 0)});
		
		poly1 = poly1.multiply(poly2); 
	}

}
