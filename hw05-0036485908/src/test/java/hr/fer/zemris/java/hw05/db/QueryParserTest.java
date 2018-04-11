package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class QueryParserTest {
	
	QueryParser queryParser;
	
	@Test
	public void templateTestDirect() {
		 queryParser = new QueryParser(" jmbag =\"0123456789\" ");
		 queryParser.parse();
		 
		 Assert.assertTrue(queryParser.isDirectQuery());
		 Assert.assertEquals("0123456789", queryParser.getQueriedJMBAG());
		 Assert.assertEquals(1, queryParser.getQuery().size());
	}
	
	@Test
	public void templateTestNonDirect() {
		 QueryParser queryParser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		 queryParser.parse();
		 
		 Assert.assertFalse(queryParser.isDirectQuery());
		 Assert.assertEquals(2, queryParser.getQuery().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void templateGetQueriedJmbagAfterNonDirectQuery() {
		 QueryParser queryParser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		 queryParser.getQueriedJMBAG(); // would throw!
	}

}
