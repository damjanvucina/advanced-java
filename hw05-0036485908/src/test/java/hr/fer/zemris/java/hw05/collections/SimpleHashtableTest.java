package hr.fer.zemris.java.hw05.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class SimpleHashtableTest {
	
	SimpleHashtable<String, Integer> table;
	
	@Before
	public void setUp() {
		table = new SimpleHashtable<String, Integer>(2);
		
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		table.put("Ivana", 5);
	}
	
	@Test
	public void putNewElementTest() {
		table.put("Marin", 3);
		Assert.assertEquals(5, table.size());
	}
	
	@Test
	public void putExistingEntrySameKeySameValueTest() {
		table.put("Jasna", 2);
		Assert.assertEquals(4, table.size());
	}
	
	@Test
	public void putExistingEntrySameKeyDifferentValueTest() {
		table.put("Jasna", 3);
		Assert.assertEquals(4, table.size());
	}
	
	@Test
	public void getFromEmptyTableTest() {
		SimpleHashtable<String, Integer> emptyTable = new SimpleHashtable<>();
		Assert.assertEquals(null, emptyTable.get("Non existing element"));
	}
	
	@Test
	public void getRegularTest() {
		Assert.assertEquals(Integer.valueOf(5), table.get("Ivana"));
	}
	
	@Test
	public void sizeRegularTest() {
		Assert.assertEquals(4, table.size());
	}
	
	@Test
	public void sizeEmptyTableTest() {
		Assert.assertEquals(0, new SimpleHashtable<>().size());
	}
	
	@Test
	public void sizeAfterPuttingNewElementTest() {
		table.put("Ivana", 4);
		table.put("Josipa", 4);
		Assert.assertEquals(5, table.size());
	}
	
	@Test
	public void containsKeyEmptyTableTest() {
		Assert.assertEquals(false, table.containsKey("Non existing"));
	}
	
	@Test
	public void containsKeyRegularTrueTest() {
		Assert.assertEquals(true, table.containsKey("Ivana"));
	}
	
	@Test
	public void containsKeyRegularFalseTest() {
		Assert.assertEquals(false, table.containsKey("Franka"));
	}
	
	@Test
	public void containsValueEmptyTableTest() {
		Assert.assertEquals(false, table.containsValue(Integer.valueOf(0)));
	}
	
	@Test
	public void containsValueRegularTrueTest() {
		Assert.assertEquals(true, table.containsValue(Integer.valueOf(2)));
	}
	
	@Test
	public void containsValueRegularFalseTest() {
		Assert.assertEquals(false, table.containsValue(Integer.valueOf(0)));
	}
	
	@Test
	public void removeRegularTest() {
		Assert.assertEquals(true, table.containsKey("Ivana"));
		table.remove("Ivana");
		Assert.assertEquals(false, table.containsKey("Ivana"));
	}
	
	@Test
	public void isEmptyTrueTest() {
		Assert.assertEquals(true, new SimpleHashtable<>().isEmpty());
	}
	
	@Test
	public void isEmptyFalseTest() {
		Assert.assertEquals(false, table.isEmpty());
	}
	
	@Test
	public void toStringTest() {
		String output = "[Ante=2, Ivana=5, Jasna=2, Kristina=5]";
		Assert.assertEquals(output, table.toString());
	}
	
}
