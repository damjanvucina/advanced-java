package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectMultistackTest {

	ObjectMultistack multistack;

	@Before
	public void setUp() {
		multistack = new ObjectMultistack();
	}

	@Test
	public void emptyMultistackTrueTest() {
		Assert.assertTrue(multistack.isEmpty("non-existing"));
	}

	@Test
	public void emptyMultistackFalseTest() {
		multistack.push("First", new ValueWrapper(null));
		Assert.assertFalse(multistack.isEmpty("First"));
	}

	@Test(expected = NullPointerException.class)
	public void nullKeyTest() {
		multistack.push(null, new ValueWrapper("irrelevant"));
	}
	
	@Test(expected = NullPointerException.class)
	public void nonEncapsulatedNullValueTest() {
		multistack.push("First", null);
	}
	
	@Test
	public void encapsulatedNullValueTest() {
		multistack.push("First", new ValueWrapper(null));
		Assert.assertFalse(multistack.isEmpty("First"));
	}

	@Test
	public void emptyStringKeyTest() {
		multistack.push("", new ValueWrapper("irrelevant"));
		Assert.assertFalse(multistack.isEmpty(""));
	}

	@Test(expected = ObjectMultistackException.class)
	public void popFromEmptyMultistackTest() {
		multistack.pop("irrelevant");
	}

	@Test(expected = ObjectMultistackException.class)
	public void peekFromEmptyMultistackTest() {
		multistack.peek("irrelevant");
	}

	@Test
	public void peekRegularTest() {
		multistack.push("First", new ValueWrapper(Integer.valueOf(1)));
		multistack.push("First", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("First", new ValueWrapper(Integer.valueOf(3)));

		Assert.assertEquals(new ValueWrapper(3), multistack.peek("First"));
		Assert.assertEquals(new ValueWrapper(3), multistack.peek("First"));
		Assert.assertEquals(new ValueWrapper(3), multistack.peek("First"));
	}

	@Test
	public void popRegularTest() {
		multistack.push("First", new ValueWrapper(Integer.valueOf(1)));
		multistack.push("First", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("First", new ValueWrapper(Integer.valueOf(3)));

		Assert.assertEquals(new ValueWrapper(3), multistack.pop("First"));
		Assert.assertEquals(new ValueWrapper(2), multistack.pop("First"));
		Assert.assertEquals(new ValueWrapper(1), multistack.pop("First"));
	}

	@Test
	public void templateTest() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		
		Assert.assertEquals(2000, multistack.peek("year").getValue());
		Assert.assertEquals(200.51, multistack.peek("price").getValue());
		
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		Assert.assertEquals(1900, multistack.peek("year").getValue());
		
		multistack.peek("year").setValue(((Integer) multistack.peek("year").getValue()).intValue() + 50);
		Assert.assertEquals(1950, multistack.peek("year").getValue());
		
		multistack.pop("year");
		Assert.assertEquals(2000, multistack.peek("year").getValue());
		
		multistack.peek("year").add("5");
		Assert.assertEquals(2005, multistack.peek("year").getValue());
		
		multistack.peek("year").add(5);
		Assert.assertEquals(2010, multistack.peek("year").getValue());
		
		multistack.peek("year").add(5.0);
		Assert.assertEquals(2015.0, multistack.peek("year").getValue());
	}
	
	@Test
	public void integrationTest() {
		multistack.push("First", new ValueWrapper(null));
		multistack.peek("First").add("1.1");
		Assert.assertEquals(1.1, multistack.peek("First").getValue());
		
		multistack.peek("First").multiply("1.11E1");
		Assert.assertEquals(12.21, multistack.peek("First").getValue());
		
		multistack.push("First", multistack.pop("First"));
		Assert.assertEquals(12.21, multistack.peek("First").getValue());
		
		multistack.push("First", new ValueWrapper("-1"));
		
		Assert.assertEquals(new ValueWrapper("-1"), multistack.pop("First"));
		
		Assert.assertFalse(multistack.isEmpty("First"));
		multistack.pop("First");
		Assert.assertTrue(multistack.isEmpty("First"));
	}

}
