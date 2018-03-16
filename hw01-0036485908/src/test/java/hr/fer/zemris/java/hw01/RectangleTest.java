package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {
	private static final double DELTA = 1e-15;
	
	@Test
	public void perimeterCalculationRegular() {
		Assert.assertEquals(14.93, Rectangle.calculatePerimeter(3.12, 4.345), DELTA);
	}
	
	@Test
	public void perimeterCalculationFirstArgumentZero() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(0, 1.13), DELTA);
	}
	
	@Test
	public void perimeterCalculationSecondArgumentZero() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(3.14, 0), DELTA);
	}
	
	@Test
	public void perimeterCalculationBothArgumentsZero() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(0, 0), DELTA);
	}
	
	@Test
	public void perimeterCalculationFirstArgumentNegative() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(-1.13, 2.15), DELTA);
	}
	
	@Test
	public void perimeterCalculationSecondArgumentNegative() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(2.45, -7), DELTA);
	}
	
	@Test
	public void perimeterCalculationBothArgumentsNegative() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(-5.16, -4.4), DELTA);
	}
	
	@Test
	public void surfaceCalculationRegular() {
		Assert.assertEquals(13.5564, Rectangle.calculateArea(3.12, 4.345), DELTA);
	}
	
	@Test
	public void surfaceCalculationFirstArgumentZero() {
		Assert.assertEquals(0, Rectangle.calculateArea(0, 2.37), DELTA);
	}
	
	
	@Test
	public void surfaceCalculationSecondArgumentZero() {
		Assert.assertEquals(0, Rectangle.calculateArea(1.15, 0), DELTA);
	}
	
	@Test
	public void surfaceCalculationBothArgumentsZero() {
		Assert.assertEquals(0, Rectangle.calculateArea(0, 0), DELTA);
	}
	
	
	@Test
	public void surfaceCalculationFirstArgumentNegative() {
		Assert.assertEquals(0, Rectangle.calculateArea(-1.13, 2.15), DELTA);
	}
	
	@Test
	public void surfaceCalculationSecondArgumentNegative() {
		Assert.assertEquals(0, Rectangle.calculateArea(2.45, -7), DELTA);
	}
	
	@Test
	public void surfaceCalculationBothArgumentsNegative() {
		Assert.assertEquals(0, Rectangle.calculatePerimeter(-5.16, -4.4), DELTA);
	}
	


}
