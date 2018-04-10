package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldValueGettersTest {
	
	IFieldValueGetter strategy;
	StudentRecord testRecord;
	
	@Before
	public void setUp() {
		testRecord = new StudentRecord("0123456789", "Testic", "Testko", 5);
	}
	
	@Test
	public void testFirstNameTrue() {
		strategy = FieldValueGetters.FIRST_NAME;
		Assert.assertEquals("Testko", strategy.get(testRecord));
	}
	
	@Test
	public void testFirstNameFalse() {
		strategy = FieldValueGetters.FIRST_NAME;
		Assert.assertNotEquals("Not Testko", strategy.get(testRecord));
	}
	
	@Test
	public void testLastNameTrue() {
		strategy = FieldValueGetters.LAST_NAME;
		Assert.assertEquals("Testic", strategy.get(testRecord));
	}
	
	@Test
	public void testLastNameFalse() {
		strategy = FieldValueGetters.LAST_NAME;
		Assert.assertNotEquals("Not Testic", strategy.get(testRecord));
	}
	
	@Test
	public void testJmbagTrue() {
		strategy = FieldValueGetters.JMBAG;
		Assert.assertEquals("0123456789", strategy.get(testRecord));
	}
	
	@Test
	public void testJmbagFalse() {
		strategy = FieldValueGetters.JMBAG;
		Assert.assertNotEquals("0000000000", strategy.get(testRecord));
	}

}
