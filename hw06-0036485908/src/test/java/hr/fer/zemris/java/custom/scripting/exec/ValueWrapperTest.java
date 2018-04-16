package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {

	public static final double DELTA = 1e-8;

	ValueWrapper value;
	Object argument;

	@Test
	public void testAdditionBothOperandsNull() {
		value = new ValueWrapper(null);
		argument = null;
		value.add(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), value.getValue());
		Assert.assertEquals(null, argument);
	}

	@Test
	public void testMultiplicationBothOperandsNull() {
		value = new ValueWrapper(null);
		argument = null;
		value.add(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), value.getValue());
		Assert.assertEquals(null, argument);
	}

	@Test
	public void testSubtractionBothOperandsNull() {
		value = new ValueWrapper(null);
		argument = null;
		value.add(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), value.getValue());
		Assert.assertEquals(null, argument);
	}

	@Test(expected = ObjectMultistackException.class)
	public void testDivisionByZero() {
		value = new ValueWrapper(null);
		argument = null;

		value.divide(argument);
	}

	@Test
	public void testMultiplicationByZero() {
		value = new ValueWrapper(null);
		argument = null;
		value.multiply(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), (Integer) value.getValue(), DELTA);
		Assert.assertEquals(null, argument);
	}

	@Test
	public void testAdditionNegativeResult() {
		value = new ValueWrapper(Integer.valueOf(1));
		argument = 3.712;
		value.add(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(4.712), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof Double);
		Assert.assertEquals(Double.valueOf(3.712), (Double) argument);
	}

	@Test
	public void testSubtractionNegativeResult() {
		value = new ValueWrapper(Integer.valueOf(1));
		argument = 3.712;
		value.subtract(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(-2.712), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof Double);
		Assert.assertEquals(Double.valueOf(3.712), (Double) argument);
	}

	@Test
	public void testMultiplicationNegativeResult() {
		value = new ValueWrapper(Double.valueOf(0.763));
		argument = 3.712;
		value.multiply(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(2.832256), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof Double);
		Assert.assertEquals(Double.valueOf(3.712), (Double) argument);
	}

	@Test
	public void testDivisionNegativeResult() {
		value = new ValueWrapper(Double.valueOf(0.763));
		argument = -3.712;
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(-0.205549569), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof Double);
		Assert.assertEquals(Double.valueOf(-3.712), (Double) argument);
	}

	@Test
	public void testDivisionBothOperandsInteger() {
		value = new ValueWrapper(Integer.valueOf(3));
		argument = 2;
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(1), (Integer) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof Integer);
		Assert.assertEquals(Integer.valueOf(2), (Integer) argument);
	}

	@Test
	public void testStringParsingToIntegerZeroResult() {
		value = new ValueWrapper("-13");
		argument = "14";
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), (Integer) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof String);
		Assert.assertEquals("14", argument.toString());
	}

	@Test
	public void testStringParsingToIntegerNegativeResult() {
		value = new ValueWrapper("-13");
		argument = "3";
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(-4), (Integer) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof String);
		Assert.assertEquals("3", argument.toString());
	}

	@Test
	public void testStringParsingToDoublePositiveResult() {
		value = new ValueWrapper("-1.2345");
		argument = "-0.716";
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(1.724162011), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof String);
		Assert.assertEquals("-0.716", argument.toString());
	}

	@Test
	public void testStringParsingToDoubleNegativeResult() {
		value = new ValueWrapper("-1.2345");
		argument = "0.716";
		value.divide(argument);

		Assert.assertTrue(value.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(-1.724162011), (Double) value.getValue(), DELTA);
		Assert.assertTrue(argument instanceof String);
		Assert.assertEquals("0.716", argument.toString());
	}

	@Test(expected = ObjectMultistackException.class)
	public void testStringExcWhileParsing() {
		value = new ValueWrapper("-13");
		argument = "Can't parse me";
		value.divide(argument);

	}

	@Test
	public void templateTest1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

		Assert.assertTrue(v1.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(0), (Integer) v1.getValue());
		Assert.assertTrue(v2.getValue() == null);
	}

	@Test
	public void templateTest2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

		Assert.assertTrue(v3.getValue() instanceof Double);
		Assert.assertEquals(Double.valueOf(13), (Double) v3.getValue());
		Assert.assertTrue(v4.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(1), (Integer) v4.getValue());
	}

	@Test
	public void templateTest3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).

		Assert.assertTrue(v5.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(13), (Integer) v5.getValue());
		Assert.assertTrue(v6.getValue() instanceof Integer);
		Assert.assertEquals(Integer.valueOf(1), (Integer) v6.getValue());
	}

	@Test(expected = ObjectMultistackException.class)
	public void templateTest4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue()); // throws RuntimeException
	}

	@Test
	public void numCompareTestBothNull() {
		value = new ValueWrapper(null);
		argument = null;

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestValueNullArgumentIntegerZero() {
		value = new ValueWrapper(null);
		argument = Integer.valueOf(0);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestValueNullArgumentDoubleZero() {
		value = new ValueWrapper(null);
		argument = Double.valueOf(0);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestValueIntegerZeroArgumentNull() {
		value = new ValueWrapper(Integer.valueOf(0));
		argument = null;

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestValueDoubleZeroArgumentNull() {
		value = new ValueWrapper(Double.valueOf(0));
		argument = null;

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestValueNullFirstLesser() {
		value = new ValueWrapper(null);
		argument = Double.valueOf(0.1);

		Assert.assertTrue(value.numCompare(argument) < 0);
	}

	@Test
	public void numCompareTestValueNullFirstBigger() {
		value = new ValueWrapper(Double.valueOf(0.1));
		argument = null;

		Assert.assertTrue(value.numCompare(argument) > 0);
	}

	@Test
	public void numCompareTestBothDoubleEqual() {
		value = new ValueWrapper(Double.valueOf(0.123));
		argument = Double.valueOf(0.123);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestIntegerAndDoubleEqual() {
		value = new ValueWrapper(Double.valueOf(-5));
		argument = Integer.valueOf(-5);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestStringAndDoubleEqual() {
		value = new ValueWrapper("-0.12");
		argument = Double.valueOf(-0.12);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareTestStringAndIntegerEqual() {
		value = new ValueWrapper("12");
		argument = Integer.valueOf(12);

		Assert.assertEquals(0, value.numCompare(argument));
	}

	@Test
	public void numCompareBothStringFirstLesser() {
		value = new ValueWrapper("12");
		argument = "13";

		Assert.assertTrue(value.numCompare(argument) < 0);
	}

	@Test
	public void numCompareBothStringFirstBigger() {
		value = new ValueWrapper("15");
		argument = "13";

		Assert.assertTrue(value.numCompare(argument) > 0);
	}

	@Test(expected = ObjectMultistackException.class)
	public void numCompareNonParsable() {
		value = new ValueWrapper("Can't parse me");
		argument = "13";

		value.numCompare(argument);
	}

}
