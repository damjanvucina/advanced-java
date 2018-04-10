package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorsTest {
	
	IComparisonOperator operator;
	
	@Test
	public void testLess() {
		operator = ComparisonOperators.LESS;
		Assert.assertTrue(operator.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessOrEqualsWithLess() {
		operator = ComparisonOperators.LESS_OR_EQUALS;
		Assert.assertTrue(operator.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessOrEqualsWithEquals() {
		operator = ComparisonOperators.LESS_OR_EQUALS;
		Assert.assertTrue(operator.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreater() {
		operator = ComparisonOperators.GREATER;
		Assert.assertTrue(operator.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEqualsWithGreater() {
		operator = ComparisonOperators.GREATER_OR_EQUALS;
		Assert.assertTrue(operator.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterWithEquals() {
		operator = ComparisonOperators.GREATER_OR_EQUALS;
		Assert.assertTrue(operator.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void testEqualsTrue() {
		operator = ComparisonOperators.EQUALS;
		Assert.assertTrue(operator.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void testEqualsFalse() {
		operator = ComparisonOperators.EQUALS;
		Assert.assertFalse(operator.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testNotEqualsTrue() {
		operator = ComparisonOperators.NOT_EQUALS;
		Assert.assertTrue(operator.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testNotEqualsFalse() {
		operator = ComparisonOperators.NOT_EQUALS;
		Assert.assertFalse(operator.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void testLikeNoAsteriskTrue() {
		operator = ComparisonOperators.LIKE;
		Assert.assertTrue(operator.satisfied("Zagreb", "Zagreb"));
	}
	
	@Test
	public void testLikeNoAsteriskFalse() {
		operator = ComparisonOperators.LIKE;
		Assert.assertFalse(operator.satisfied("Split", "Zagreb"));
	}
	
	@Test
	public void testLikeAsteriskInTheBeginningTrue() {
		operator = ComparisonOperators.LIKE;
		Assert.assertTrue(operator.satisfied("Zagreb", "*eb"));
	}
	
	@Test
	public void testLikeAsteriskInTheBeginningFalse() {
		operator = ComparisonOperators.LIKE;
		Assert.assertFalse(operator.satisfied("Zagreb", "*t"));
	}
	
	@Test
	public void testLikeAsteriskInTheEndTrue() {
		operator = ComparisonOperators.LIKE;
		Assert.assertTrue(operator.satisfied("Zagreb", "Zag*"));
	}
	
	@Test
	public void testLikeAsteriskInTheEndFalse() {
		operator = ComparisonOperators.LIKE;
		Assert.assertFalse(operator.satisfied("Zagreb", "Sp*"));
	}
	
	@Test
	public void testLikeAsteriskInTheMiddleTrue() {
		operator = ComparisonOperators.LIKE;
		Assert.assertTrue(operator.satisfied("Zagreb", "Z*eb"));
	}
	
	@Test
	public void testLikeAsteriskInTheMiddleFalse() {
		operator = ComparisonOperators.LIKE;
		Assert.assertFalse(operator.satisfied("Zagreb", "Sp*t"));
	}
	
	@Test(expected=StudentDatabaseException.class)
	public void testMultipleAsterisks() {
		operator = ComparisonOperators.LIKE;
		operator.satisfied("Zagreb", "Multiple**asterisks");
	}
	
	
	
	
	

}
