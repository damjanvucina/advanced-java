package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {
	
	@Test
	public void regularInput() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test
	public void minimumInput() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}
	
	@Test
	public void maximumInput() {
		Assert.assertEquals(2432902008176640000L, Factorial.calculateFactorial(20));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeInput() {
		Factorial.calculateFactorial(-1);
	}

}
