package hr.fer.zemris.java.hw02;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexNumberTest {
	
	private static final double DELTA = 1e-3;
	
	ComplexNumber complexNumber1;
	ComplexNumber complexNumber2;
	ComplexNumber complexNumber3;
	ComplexNumber complexNumber4;
	ComplexNumber complexNumber5;
	
	@Before
	public void initialize() {
		complexNumber1 = new ComplexNumber(-1.13, -2.15);
		complexNumber2 = new ComplexNumber(0.57, -3.11);
		complexNumber3 = new ComplexNumber(13.3, 0);
		complexNumber4 = new ComplexNumber(0, 3);
		complexNumber5 = new ComplexNumber(0, 0);
	}
	
	@Test
	public void getRealTest() {
		Assert.assertEquals(-1.13, complexNumber1.getReal(), DELTA);
		Assert.assertEquals(0.57, complexNumber2.getReal(), DELTA);
		Assert.assertEquals(13.3, complexNumber3.getReal(), DELTA);
		Assert.assertEquals(0, complexNumber4.getReal(), DELTA);
		Assert.assertEquals(0, complexNumber5.getReal(), DELTA);
	}
	
	@Test
	public void getImaginaryTest() {
		Assert.assertEquals(-2.15, complexNumber1.getImaginary(), DELTA);
		Assert.assertEquals(-3.11, complexNumber2.getImaginary(), DELTA);
		Assert.assertEquals(0, complexNumber3.getImaginary(), DELTA);
		Assert.assertEquals(3, complexNumber4.getImaginary(), DELTA);
		Assert.assertEquals(0, complexNumber5.getImaginary(), DELTA);
	}
	
	@Test
	public void getMagnitudeTest() {
		Assert.assertEquals(2.42887, complexNumber1.getMagnitude(), DELTA);
		Assert.assertEquals(3.1618, complexNumber2.getMagnitude(), DELTA);
		Assert.assertEquals(13.3, complexNumber3.getMagnitude(), DELTA);
		Assert.assertEquals(3, complexNumber4.getMagnitude(), DELTA);
		Assert.assertEquals(0, complexNumber5.getMagnitude(), DELTA);
	}
	
	@Test
	public void getAngleTest() {
		Assert.assertEquals(4.228489, complexNumber1.getAngle(), DELTA);
		Assert.assertEquals(4.8936, complexNumber2.getAngle(), DELTA);
		Assert.assertEquals(0, complexNumber3.getAngle(), DELTA);
		Assert.assertEquals(1.570796, complexNumber4.getAngle(), DELTA);
		Assert.assertEquals(0, complexNumber5.getAngle(), DELTA);
	}
	
	@Test
	public void fromRealTest() {
		assertEquals(complexNumber3, ComplexNumber.fromReal(13.3));
		assertEquals(complexNumber5, ComplexNumber.fromReal(0));
	}
	
	@Test
	public void fromImaginaryTest() {
		assertEquals(complexNumber4, ComplexNumber.fromImaginary(3));
		assertEquals(complexNumber5, ComplexNumber.fromImaginary(0));
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		Assert.assertEquals(-1.13, ComplexNumber.fromMagnitudeAndAngle(2.42887, 4.228489).getReal(), DELTA);
		Assert.assertEquals(-2.15, ComplexNumber.fromMagnitudeAndAngle(2.42887, 4.228489).getImaginary(), DELTA);
	
		Assert.assertEquals(0.57, ComplexNumber.fromMagnitudeAndAngle(3.1618, 4.8936).getReal(), DELTA);
		Assert.assertEquals(-3.11, ComplexNumber.fromMagnitudeAndAngle(3.1618, 4.8936).getImaginary(), DELTA);
		
		Assert.assertEquals(13.3, ComplexNumber.fromMagnitudeAndAngle(13.3, 0).getReal(), DELTA);
		Assert.assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(13.3, 0).getImaginary(), DELTA);
		
		Assert.assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(3, 1.570796).getReal(), DELTA);
		Assert.assertEquals(3, ComplexNumber.fromMagnitudeAndAngle(3, 1.570796).getImaginary(), DELTA);
		
		Assert.assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getReal(), DELTA);
		Assert.assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getImaginary(), DELTA);
	}
	
	@Test
	public void addTest() {
		Assert.assertEquals(-0.56, complexNumber1.add(complexNumber2).getReal(), DELTA);
		Assert.assertEquals(-5.26, complexNumber1.add(complexNumber2).getImaginary(), DELTA);
		
		Assert.assertEquals(13.87, complexNumber2.add(complexNumber3).getReal(), DELTA);
		Assert.assertEquals(-3.11, complexNumber2.add(complexNumber3).getImaginary(), DELTA);
		
		Assert.assertEquals(13.3, complexNumber3.add(complexNumber4).getReal(), DELTA);
		Assert.assertEquals(3, complexNumber3.add(complexNumber4).getImaginary(), DELTA);
		
		Assert.assertEquals(0, complexNumber4.add(complexNumber5).getReal(), DELTA);
		Assert.assertEquals(3, complexNumber4.add(complexNumber5).getImaginary(), DELTA);
	}
	
	@Test
	public void subTest() {
		Assert.assertEquals(-1.7, complexNumber1.sub(complexNumber2).getReal(), DELTA);
		Assert.assertEquals(0.96, complexNumber1.sub(complexNumber2).getImaginary(), DELTA);
		
		Assert.assertEquals(-12.73, complexNumber2.sub(complexNumber3).getReal(), DELTA);
		Assert.assertEquals(-3.11, complexNumber2.sub(complexNumber3).getImaginary(), DELTA);
		
		Assert.assertEquals(13.3, complexNumber3.sub(complexNumber4).getReal(), DELTA);
		Assert.assertEquals(-3, complexNumber3.sub(complexNumber4).getImaginary(), DELTA);
		
		Assert.assertEquals(0, complexNumber4.sub(complexNumber5).getReal(), DELTA);
		Assert.assertEquals(3, complexNumber4.sub(complexNumber5).getImaginary(), DELTA);
	}
	
	
	
}
