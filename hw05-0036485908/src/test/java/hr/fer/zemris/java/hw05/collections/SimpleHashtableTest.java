package hr.fer.zemris.java.hw05.collections;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class SimpleHashtableTest {
	
	SimpleHashtable<String, Integer> simpleHashtable;
	
	@Before
	public void setUp() {
		simpleHashtable = new SimpleHashtable<String, Integer>(2);
		
		simpleHashtable.put("Ivana", 2);
		simpleHashtable.put("Ante", 2);
		simpleHashtable.put("Jasna", 2);
		simpleHashtable.put("Kristina", 5);
		simpleHashtable.put("Ivana", 5);
	}
	
	@Test
	public void putNewElementTest() {
		simpleHashtable.put("Marin", 3);
		Assert.assertEquals(5, simpleHashtable.size());
	}
	
	@Test
	public void putExistingEntrySameKeySameValueTest() {
		simpleHashtable.put("Jasna", 2);
		Assert.assertEquals(4, simpleHashtable.size());
	}
	
	@Test
	public void putExistingEntrySameKeyDifferentValueTest() {
		simpleHashtable.put("Jasna", 3);
		Assert.assertEquals(4, simpleHashtable.size());
	}
	
	@Test
	public void getFromEmptyTableTest() {
		SimpleHashtable<String, Integer> emptyTable = new SimpleHashtable<>();
		Assert.assertEquals(null, emptyTable.get("Non existing element"));
	}
	
	@Test
	public void getRegularTest() {
		Assert.assertEquals(Integer.valueOf(5), simpleHashtable.get("Ivana"));
	}
	
	@Test
	public void sizeRegularTest() {
		Assert.assertEquals(4, simpleHashtable.size());
	}
	
	@Test
	public void sizeEmptyTableTest() {
		Assert.assertEquals(0, new SimpleHashtable<>().size());
	}
	
	@Test
	public void sizeAfterPuttingNewElementTest() {
		simpleHashtable.put("Ivana", 4);
		simpleHashtable.put("Josipa", 4);
		Assert.assertEquals(5, simpleHashtable.size());
	}
	
	@Test
	public void containsKeyEmptyTableTest() {
		Assert.assertEquals(false, simpleHashtable.containsKey("Non existing"));
	}
	
	@Test
	public void containsKeyRegularTrueTest() {
		Assert.assertEquals(true, simpleHashtable.containsKey("Ivana"));
	}
	
	@Test
	public void containsKeyRegularFalseTest() {
		Assert.assertEquals(false, simpleHashtable.containsKey("Franka"));
	}
	
	@Test
	public void containsValueEmptyTableTest() {
		Assert.assertEquals(false, simpleHashtable.containsValue(Integer.valueOf(0)));
	}
	
	@Test
	public void containsValueRegularTrueTest() {
		Assert.assertEquals(true, simpleHashtable.containsValue(Integer.valueOf(2)));
	}
	
	@Test
	public void containsValueRegularFalseTest() {
		Assert.assertEquals(false, simpleHashtable.containsValue(Integer.valueOf(0)));
	}
	
	@Test
	public void removeRegularTest() {
		Assert.assertEquals(true, simpleHashtable.containsKey("Ivana"));
		simpleHashtable.remove("Ivana");
		Assert.assertEquals(false, simpleHashtable.containsKey("Ivana"));
	}
	
	@Test
	public void isEmptyTrueTest() {
		Assert.assertEquals(true, new SimpleHashtable<>().isEmpty());
	}
	
	@Test
	public void isEmptyFalseTest() {
		Assert.assertEquals(false, simpleHashtable.isEmpty());
	}
	
	@Test
	public void toStringTest() {
		String output = "[Ante=2, Ivana=5, Jasna=2, Kristina=5]";
		Assert.assertEquals(output, simpleHashtable.toString());
	}
	
	//------------------------------------------------------------------------
	//						Problem 2: Testing
	//------------------------------------------------------------------------
	
	@Test
	public void testTableReallocationDueToLoadFactor() {
		SimpleHashtable<Integer, String> testTable = new SimpleHashtable<>(1);
		Assert.assertEquals(1, testTable.getTableLength());
		testTable.put(1, "Test value");
		Assert.assertEquals(2, testTable.getTableLength());
		testTable.put(2, "Test value");
		Assert.assertEquals(4, testTable.getTableLength());
		testTable.put(3, "Test value");
		Assert.assertEquals(8, testTable.getTableLength());
		testTable.put(4, "Test value");
		Assert.assertEquals(8, testTable.getTableLength());
		testTable.put(5, "Test value");
		Assert.assertEquals(8, testTable.getTableLength());
		testTable.put(6, "Test value");
		Assert.assertEquals(16, testTable.getTableLength());
		testTable.put(7, "Test value");
		testTable.put(8, "Test value");
		testTable.put(9, "Test value");
		testTable.put(10, "Test value");
		testTable.put(11, "Test value");
		testTable.put(12, "Test value");
		Assert.assertEquals(32, testTable.getTableLength());
	}
	
}
