package hr.fer.zemris.java.hw02;

import static org.junit.Assert.assertEquals;
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
	public void getRealRegular() {
		assertEquals(0.57, complexNumber2.getReal(), DELTA);
	}

	@Test
	public void getRealBothArgumentsNegative() {
		assertEquals(-1.13, complexNumber1.getReal(), DELTA);
	}

	@Test
	public void getRealImaginaryZero() {
		assertEquals(13.3, complexNumber3.getReal(), DELTA);
	}

	@Test
	public void getRealZero() {
		assertEquals(0, complexNumber4.getReal(), DELTA);
	}

	@Test
	public void getRealBothArgumentsZero() {
		assertEquals(0, complexNumber5.getReal(), DELTA);
	}

	@Test
	public void getImaginaryNegative() {
		assertEquals(-2.15, complexNumber1.getImaginary(), DELTA);
	}

	@Test
	public void getImaginaryBothArgumentsNegative() {
		assertEquals(-3.11, complexNumber2.getImaginary(), DELTA);
	}

	@Test
	public void getImaginaryZero() {
		assertEquals(0, complexNumber3.getImaginary(), DELTA);
	}

	@Test
	public void getImaginaryRealZero() {
		assertEquals(3, complexNumber4.getImaginary(), DELTA);
	}

	@Test
	public void getImaginaryBothArgumentsZero() {
		assertEquals(0, complexNumber5.getImaginary(), DELTA);
	}

	@Test
	public void getMagnitudeTest() {
		assertEquals(2.42887, complexNumber1.getMagnitude(), DELTA);
		assertEquals(3.1618, complexNumber2.getMagnitude(), DELTA);
		assertEquals(13.3, complexNumber3.getMagnitude(), DELTA);
		assertEquals(3, complexNumber4.getMagnitude(), DELTA);
		assertEquals(0, complexNumber5.getMagnitude(), DELTA);
	}

	@Test
	public void getAngleTest() {
		assertEquals(4.228489, complexNumber1.getAngle(), DELTA);
		assertEquals(4.8936, complexNumber2.getAngle(), DELTA);
		assertEquals(0, complexNumber3.getAngle(), DELTA);
		assertEquals(1.570796, complexNumber4.getAngle(), DELTA);
		assertEquals(0, complexNumber5.getAngle(), DELTA);
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
		assertEquals(-1.13, ComplexNumber.fromMagnitudeAndAngle(2.42887, 4.228489).getReal(), DELTA);
		assertEquals(-2.15, ComplexNumber.fromMagnitudeAndAngle(2.42887, 4.228489).getImaginary(), DELTA);

		assertEquals(0.57, ComplexNumber.fromMagnitudeAndAngle(3.1618, 4.8936).getReal(), DELTA);
		assertEquals(-3.11, ComplexNumber.fromMagnitudeAndAngle(3.1618, 4.8936).getImaginary(), DELTA);

		assertEquals(13.3, ComplexNumber.fromMagnitudeAndAngle(13.3, 0).getReal(), DELTA);
		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(13.3, 0).getImaginary(), DELTA);

		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(3, 1.570796).getReal(), DELTA);
		assertEquals(3, ComplexNumber.fromMagnitudeAndAngle(3, 1.570796).getImaginary(), DELTA);

		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getReal(), DELTA);
		assertEquals(0, ComplexNumber.fromMagnitudeAndAngle(0, 0).getImaginary(), DELTA);
	}

	@Test
	public void addTest() {
		assertEquals(-0.56, complexNumber1.add(complexNumber2).getReal(), DELTA);
		assertEquals(-5.26, complexNumber1.add(complexNumber2).getImaginary(), DELTA);

		assertEquals(13.87, complexNumber2.add(complexNumber3).getReal(), DELTA);
		assertEquals(-3.11, complexNumber2.add(complexNumber3).getImaginary(), DELTA);

		assertEquals(13.3, complexNumber3.add(complexNumber4).getReal(), DELTA);
		assertEquals(3, complexNumber3.add(complexNumber4).getImaginary(), DELTA);

		assertEquals(0, complexNumber4.add(complexNumber5).getReal(), DELTA);
		assertEquals(3, complexNumber4.add(complexNumber5).getImaginary(), DELTA);
	}

	@Test
	public void subTest() {
		assertEquals(-1.7, complexNumber1.sub(complexNumber2).getReal(), DELTA);
		assertEquals(0.96, complexNumber1.sub(complexNumber2).getImaginary(), DELTA);

		assertEquals(-12.73, complexNumber2.sub(complexNumber3).getReal(), DELTA);
		assertEquals(-3.11, complexNumber2.sub(complexNumber3).getImaginary(), DELTA);

		assertEquals(13.3, complexNumber3.sub(complexNumber4).getReal(), DELTA);
		assertEquals(-3, complexNumber3.sub(complexNumber4).getImaginary(), DELTA);

		assertEquals(0, complexNumber4.sub(complexNumber5).getReal(), DELTA);
		assertEquals(3, complexNumber4.sub(complexNumber5).getImaginary(), DELTA);
	}

	@Test
	public void mulTestRegular() {
		assertEquals(-7.3306, complexNumber1.mul(complexNumber2).getReal(), DELTA);
		assertEquals(2.2888, complexNumber1.mul(complexNumber2).getImaginary(), DELTA);
	}
	
	@Test
	public void mulTestZero() {
		assertEquals(0, complexNumber4.mul(complexNumber5).getReal(), DELTA);
		assertEquals(0, complexNumber4.mul(complexNumber5).getImaginary(), DELTA);
	}
	
	@Test
	public void divTestRegular() {
		assertEquals(0.6044213, complexNumber1.div(complexNumber2).getReal(), DELTA);
		assertEquals(-0.4741222, complexNumber1.div(complexNumber2).getImaginary(), DELTA);
	}
	
	@Test
	public void divTestZero() {
		assertEquals(-1.0366667, complexNumber2.div(complexNumber4).getReal(), DELTA);
		assertEquals(-0.19, complexNumber2.div(complexNumber4).getImaginary(), DELTA);
	}
	
	@Test
	public void powerTestRegular() {
		assertEquals(-16.354098, complexNumber2.power(3).getReal(), DELTA);
		assertEquals(27.048914, complexNumber2.power(3).getImaginary(), DELTA);
	}
	
	@Test
	public void powerTestBothArgumentsZero() {
		assertEquals(0, complexNumber5.power(2).getReal(), DELTA);
		assertEquals(0, complexNumber5.power(2).getImaginary(), DELTA);
	}
	
	@Test
	public void powerTestZeroExponent() {
		assertEquals(1, complexNumber2.power(0).getReal(), DELTA);
		assertEquals(0, complexNumber2.power(0).getImaginary(), DELTA);
		assertEquals(new ComplexNumber(1, 0), complexNumber2.power(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootTestZeroArgument() {
		assertEquals(new ComplexNumber(1.3130833, -0.6557679), complexNumber2.root(0)[0]);
	}
	
	@Test
	public void rootTestRegular() {
		assertEquals(-0.0886299, complexNumber2.root(3)[0].getReal(), DELTA);
		assertEquals(1.4650474, complexNumber2.root(3)[0].getImaginary(), DELTA);
	}
	
	@Test
	public void parseTest1() {
		assertEquals(new ComplexNumber(1, 2), ComplexNumber.parse("1+2i"));
	}
	
	@Test
	public void parseTest2() {
		assertEquals(new ComplexNumber(1, 2), ComplexNumber.parse("1+2i"));
	}
	
	@Test
	public void parseTest3() {
		assertEquals(new ComplexNumber(1, -2), ComplexNumber.parse("1-2i"));
	}
	
	@Test
	public void parseTest4() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.parse("1"));
	}
	
	@Test
	public void parseTest5() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.parse("1+0i"));
	}
	
	@Test
	public void parseTest6() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.parse("1-0i"));
	}
	
	@Test
	public void parseTest7() {
		assertEquals(new ComplexNumber(-1, 2), ComplexNumber.parse("-1+2i"));
	}
	
	@Test
	public void parseTest8() {
		assertEquals(new ComplexNumber(-1, -2), ComplexNumber.parse("-1-2i"));
	}
	
	@Test
	public void parseTest9() {
		assertEquals(new ComplexNumber(-1, 0), ComplexNumber.parse("-1"));
	}
	
	@Test
	public void parseTest10() {
		assertEquals(new ComplexNumber(-1, 0), ComplexNumber.parse("-1+0i"));
	}
	
	@Test
	public void parseTest11() {
		assertEquals(new ComplexNumber(-1, 0), ComplexNumber.parse("-1-0i"));
	}
	
	@Test
	public void parseTest12() {
		assertEquals(new ComplexNumber(0, 2), ComplexNumber.parse("0+2i"));
	}
	
	@Test
	public void parseTest13() {
		assertEquals(new ComplexNumber(0, -2), ComplexNumber.parse("0-2i"));
	}
	
	@Test
	public void parseTest14() {
		assertEquals(new ComplexNumber(0, -2), ComplexNumber.parse("0-2i"));
	}
	
	@Test
	public void parseTest15() {
		assertEquals(new ComplexNumber(0, 0), ComplexNumber.parse("0-0i"));
	}
	
	@Test
	public void parseTest16() {
		assertEquals(new ComplexNumber(0, 0), ComplexNumber.parse("0i"));
	}
	
	@Test
	public void parseTest17() {
		assertEquals(new ComplexNumber(0, 0), ComplexNumber.parse("0"));
	}
	
	@Test
	public void parseTest18() {
		assertEquals(new ComplexNumber(1.11, 2.11), ComplexNumber.parse("1.11+2.11i"));
	}
	
	@Test
	public void parseTest19() {
		assertEquals(new ComplexNumber(1.11, 2.11), ComplexNumber.parse("1.11+2.11i"));
	}
	
	@Test
	public void parseTest20() {
		assertEquals(new ComplexNumber(1.11, -2.11), ComplexNumber.parse("1.11-2.11i"));
	}
	
	@Test
	public void parseTest21() {
		assertEquals(new ComplexNumber(1.11, 0), ComplexNumber.parse("1.11"));
	}
	
	@Test
	public void parseTest22() {
		assertEquals(new ComplexNumber(1.11, 0.11), ComplexNumber.parse("1.11+0.11i"));
	}
	
	@Test
	public void parseTest23() {
		assertEquals(new ComplexNumber(1.11, -0.11), ComplexNumber.parse("1.11-0.11i"));
	}
	
	@Test
	public void parseTest24() {
		assertEquals(new ComplexNumber(-1.11, 2.11), ComplexNumber.parse("-1.11+2.11i"));
	}
	
	@Test
	public void parseTest25() {
		assertEquals(new ComplexNumber(-1.11, -2.11), ComplexNumber.parse("-1.11-2.11i"));
	}
	
	@Test
	public void parseTest26() {
		assertEquals(new ComplexNumber(-1.11, 0), ComplexNumber.parse("-1.11"));
	}
	
	@Test
	public void parseTest27() {
		assertEquals(new ComplexNumber(-1.11, 0.11), ComplexNumber.parse("-1.11+0.11i"));
	}
	
	@Test
	public void parseTest28() {
		assertEquals(new ComplexNumber(-1.11, -0.11), ComplexNumber.parse("-1.11-0.11i"));
	}
	
	@Test
	public void parseTest29() {
		assertEquals(new ComplexNumber(0.11, 2.11), ComplexNumber.parse("0.11+2.11i"));
	}
	
	@Test
	public void parseTest30() {
		assertEquals(new ComplexNumber(0.11, -2.11), ComplexNumber.parse("0.11-2.11i"));
	}
	
	@Test
	public void parseTest31() {
		assertEquals(new ComplexNumber(0.11, -2.11), ComplexNumber.parse("0.11-2.11i"));
	}
	
	@Test
	public void parseTest32() {
		assertEquals(new ComplexNumber(0.11, -0.11), ComplexNumber.parse("0.11-0.11i"));
	}
	
	@Test
	public void parseTest33() {
		assertEquals(new ComplexNumber(0, 0.11), ComplexNumber.parse("0.11i"));
	}
	
	@Test
	public void parseTest34() {
		assertEquals(new ComplexNumber(0.11, 0), ComplexNumber.parse("0.11"));
	}

}
