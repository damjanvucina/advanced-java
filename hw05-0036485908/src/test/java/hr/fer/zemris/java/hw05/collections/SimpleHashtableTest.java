package hr.fer.zemris.java.hw05.collections;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class SimpleHashtableTest<K, V> {
	
	SimpleHashtable<Integer, String> table;
	
	@Before
	private void initialize() {
		table = new SimpleHashtable<>(4);
		
		table.put(1, "First");
		table.put(2, "Second");
		table.put(3, "Third");
		table.put(4, "Fourth");
	}
	
	@Test
	public void testEmptyHashtable() {
		Assert.assertEquals(0, new SimpleHashtable<>().size());
	}
}
