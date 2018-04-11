package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class QueryParserTest {

	QueryParser parser;
	List<ConditionalExpression> expressions;

	@Test
	public void templateTestDirect() {
		parser = new QueryParser(" jmbag =\"0123456789\" ");
		parser.parse();

		Assert.assertTrue(parser.isDirectQuery());
		Assert.assertEquals("0123456789", parser.getQueriedJMBAG());
		Assert.assertEquals(1, parser.getQuery().size());
	}

	@Test
	public void templateTestNonDirect() {
		parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		parser.parse();

		Assert.assertFalse(parser.isDirectQuery());
		Assert.assertEquals(2, parser.getQuery().size());
	}

	@Test(expected = IllegalStateException.class)
	public void templateGetQueriedJmbagAfterNonDirectQuery() {
		parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		parser.getQueriedJMBAG(); // would throw!
	}

	@Test
	public void testParsingDirectQuery() {
		parser = new QueryParser("jmbag = \"0000000003\"");
		parser.parse();
		expressions = parser.getQuery();
		ConditionalExpression expectedExpression =  new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003",
				ComparisonOperators.EQUALS);

		Assert.assertTrue(parser.isDirectQuery());
		Assert.assertEquals(expectedExpression, expressions.get(0));

	}
	
	@Test
	public void testParsingNonDirectQuery() {
		parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		parser.parse();
		expressions = parser.getQuery();
		List<ConditionalExpression> expectedExpressions =  new LinkedList<>();
		expectedExpressions.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.EQUALS));
		expectedExpressions.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "B*", ComparisonOperators.LIKE));

		Assert.assertFalse(parser.isDirectQuery());
		Assert.assertEquals(expectedExpressions, expressions);

	}
	
	@Test
	public void testSingleQueryNonDirect() {
		parser = new QueryParser("lastName LIKE \"B*\"");
		parser.parse();
		expressions = parser.getQuery();
		List<ConditionalExpression> expectedExpressions =  new LinkedList<>();
		expectedExpressions.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "B*", ComparisonOperators.LIKE));

		Assert.assertFalse(parser.isDirectQuery());
		Assert.assertEquals(expectedExpressions, expressions);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidAttribute() {
		parser = new QueryParser("invalidAttribute LIKE \"B*\"");
		parser.parse();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidOperator() {
		parser = new QueryParser("lastName INVALID_OPERATOR \"B*\"");
		parser.parse();
	}
	
}
