package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {
	
	@Test
	public void regularInput() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test
	public void zeroInput() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeInput() {
		Factorial.calculateFactorial(-1);
	}

}
