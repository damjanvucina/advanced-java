package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {
	
	@Test
	public void perimeterCalculationRegular() {
		Assert.assertEquals(14.93, Rectangle.getPerimeter(3.12, 4.345), 0.001);
	}
	
	@Test
	public void perimeterCalculationFirstArgumentZero() {
		Assert.assertEquals(0, Rectangle.getPerimeter(0, 1.13), 0.001);
	}
	
	@Test
	public void perimeterCalculationSecondArgumentZero() {
		Assert.assertEquals(0, Rectangle.getPerimeter(3.14, 0), 0.001);
	}
	
	@Test
	public void perimeterCalculationBothArgumentsZero() {
		Assert.assertEquals(0, Rectangle.getPerimeter(0, 0), 0.001);
	}
	
	@Test
	public void perimeterCalculationFirstArgumentNegative() {
		Assert.assertEquals(0, Rectangle.getPerimeter(-1.13, 2.15), 0.001);
	}
	
	@Test
	public void perimeterCalculationSecondArgumentNegative() {
		Assert.assertEquals(0, Rectangle.getPerimeter(2.45, -7), 0.001);
	}
	
	@Test
	public void perimeterCalculationBothArgumentsNegative() {
		Assert.assertEquals(0, Rectangle.getPerimeter(-5.16, -4.4), 0.001);
	}
	
	@Test
	public void surfaceCalculationRegular() {
		Assert.assertEquals(13.5564, Rectangle.getSurface(3.12, 4.345), 0.001);
	}
	
	@Test
	public void surfaceCalculationFirstArgumentZero() {
		Assert.assertEquals(0, Rectangle.getSurface(0, 2.37), 0.001);
	}
	
	
	@Test
	public void surfaceCalculationSecondArgumentZero() {
		Assert.assertEquals(0, Rectangle.getSurface(1.15, 0), 0.001);
	}
	
	@Test
	public void surfaceCalculationBothArgumentsZero() {
		Assert.assertEquals(0, Rectangle.getSurface(0, 0), 0.001);
	}
	
	
	@Test
	public void surfaceCalculationFirstArgumentNegative() {
		Assert.assertEquals(0, Rectangle.getSurface(-1.13, 2.15), 0.001);
	}
	
	@Test
	public void surfaceCalculationSecondArgumentNegative() {
		Assert.assertEquals(0, Rectangle.getSurface(2.45, -7), 0.001);
	}
	
	@Test
	public void surfaceCalculationBothArgumentsNegative() {
		Assert.assertEquals(0, Rectangle.getPerimeter(-5.16, -4.4), 0.001);
	}
	


}
