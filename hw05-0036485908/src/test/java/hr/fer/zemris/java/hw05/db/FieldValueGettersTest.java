package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldValueGettersTest {
	
	IFieldValueGetter getter;
	StudentRecord testRecord;
	
	@Before
	public void setUp() {
		testRecord = new StudentRecord("0123456789", "Testic", "Testko", 5);
	}
	
	@Test
	public void testFirstNameTrue() {
		getter = FieldValueGetters.FIRST_NAME;
		Assert.assertEquals("Testko", getter.get(testRecord));
	}
	
	@Test
	public void testFirstNameFalse() {
		getter = FieldValueGetters.FIRST_NAME;
		Assert.assertNotEquals("Not Testko", getter.get(testRecord));
	}
	
	@Test
	public void testLastNameTrue() {
		getter = FieldValueGetters.LAST_NAME;
		Assert.assertEquals("Testic", getter.get(testRecord));
	}
	
	@Test
	public void testLastNameFalse() {
		getter = FieldValueGetters.LAST_NAME;
		Assert.assertNotEquals("Not Testic", getter.get(testRecord));
	}
	
	@Test
	public void testJmbagTrue() {
		getter = FieldValueGetters.JMBAG;
		Assert.assertEquals("0123456789", getter.get(testRecord));
	}
	
	@Test
	public void testJmbagFalse() {
		getter = FieldValueGetters.JMBAG;
		Assert.assertNotEquals("0000000000", getter.get(testRecord));
	}

}
