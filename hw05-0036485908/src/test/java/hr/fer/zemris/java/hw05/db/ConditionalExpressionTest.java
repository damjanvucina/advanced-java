package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConditionalExpressionTest {

	ConditionalExpression expr;
	StudentRecord record;
	boolean recordSatisfied;

	@Before
	public void setUp() {
		record = new StudentRecord("0123456789", "Bosnic", "Testko", 5);
	}

	@Test
	public void templateTest() {
		expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
		recordSatisfied = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());

		Assert.assertTrue(recordSatisfied);
	}

	@Test
	public void templateTestFalse() {
		recordSatisfied = extractAndTest(FieldValueGetters.LAST_NAME, "Strange*Template", ComparisonOperators.LIKE);
		Assert.assertFalse(recordSatisfied);
	}

	@Test(expected = StudentDatabaseException.class)
	public void testMultipleAsterisk() {
		recordSatisfied = extractAndTest(FieldValueGetters.LAST_NAME, "*Too*Many*Asterisks*", ComparisonOperators.LIKE);
	}

	@Test
	public void testAsteriskInTheBeginning() {
		recordSatisfied = extractAndTest(FieldValueGetters.FIRST_NAME, "*tko", ComparisonOperators.LIKE);
		Assert.assertTrue(recordSatisfied);
	}

	@Test
	public void testAsteriskInTheEnd() {
		recordSatisfied = extractAndTest(FieldValueGetters.FIRST_NAME, "*tko", ComparisonOperators.LIKE);
		Assert.assertTrue(recordSatisfied);
	}
	
	@Test
	public void testAsteriskInTheMiddle() {
		recordSatisfied = extractAndTest(FieldValueGetters.FIRST_NAME, "Te*o", ComparisonOperators.LIKE);
		Assert.assertTrue(recordSatisfied);
	}
	
	private boolean extractAndTest(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		
		expr = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
		
		return expr.getComparisonOperator()
				.satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral());

	}

}
