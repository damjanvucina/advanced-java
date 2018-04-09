package hr.fer.zemris.java.hw05.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

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

	// ------------------------------------------------------------------------
	// Problem 1: Testing SimpleHashtable methods
	// ------------------------------------------------------------------------

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

	// ------------------------------------------------------------------------
	// Problem 2: Testing table reallocation
	// ------------------------------------------------------------------------

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

	// ------------------------------------------------------------------------
	// Problem 3: Testing using Iterator
	// ------------------------------------------------------------------------

	@Test
	public void testRegularPrintingElements() {
		String testString = "Ante => 2\r\n" + "Ivana => 5\r\n" + "Jasna => 2\r\n" + "Kristina => 5";
		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : simpleHashtable) {
			sb.append(pair.getKey()).append(" => ").append(pair.getValue()).append("\r\n");
		}
		sb.delete(sb.lastIndexOf("\r"), sb.length());
		Assert.assertEquals(testString, sb.toString());
	}

	@Test
	public void testCartesianProduct() {
		StringBuilder sb = new StringBuilder();
		String testString = "(Ante => 2) - (Ante => 2)\r\n" + "(Ante => 2) - (Ivana => 5)\r\n"
				+ "(Ante => 2) - (Jasna => 2)\r\n" + "(Ante => 2) - (Kristina => 5)\r\n"
				+ "(Ivana => 5) - (Ante => 2)\r\n" + "(Ivana => 5) - (Ivana => 5)\r\n"
				+ "(Ivana => 5) - (Jasna => 2)\r\n" + "(Ivana => 5) - (Kristina => 5)\r\n"
				+ "(Jasna => 2) - (Ante => 2)\r\n" + "(Jasna => 2) - (Ivana => 5)\r\n"
				+ "(Jasna => 2) - (Jasna => 2)\r\n" + "(Jasna => 2) - (Kristina => 5)\r\n"
				+ "(Kristina => 5) - (Ante => 2)\r\n" + "(Kristina => 5) - (Ivana => 5)\r\n"
				+ "(Kristina => 5) - (Jasna => 2)\r\n" + "(Kristina => 5) - (Kristina => 5)";

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : simpleHashtable) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : simpleHashtable) {
				sb.append("(").append(pair1.getKey()).append(" => ").append(pair1.getValue()).append(") - (")
						.append(pair2.getKey()).append(" => ").append(pair2.getValue()).append(")").append("\r\n");
			}
		}

		sb.delete(sb.lastIndexOf("\r"), sb.length());
		Assert.assertEquals(testString, sb.toString());
	}

	@Test
	public void testRemoveUsingIterator() {
		Assert.assertEquals(true, simpleHashtable.containsKey("Ivana"));

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = simpleHashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}

		Assert.assertEquals(false, simpleHashtable.containsKey("Ivana"));
	}

	@Test(expected = IllegalStateException.class)
	public void testSuccessiveInvokingRemove() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = simpleHashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testEditingHashtableFromOutside() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = simpleHashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				simpleHashtable.remove("Ivana");
			}
		}
	}

	@Test
	public void testPrintingAndRemovingElements() {
		String testString = "Ante => 2\r\n" + "Ivana => 5\r\n" + "Jasna => 2\r\n" + "Kristina => 5\r\n"
				+ "Veličina: 0\r\n" + "";
		StringBuilder sb = new StringBuilder();

		Assert.assertEquals(4, simpleHashtable.size());

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = simpleHashtable.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			sb.append(pair.getKey()).append(" => ").append(pair.getValue()).append("\r\n");
			iter.remove();
		}
		sb.append("Veličina: ").append(simpleHashtable.size()).append("\r\n");

		Assert.assertEquals(testString, sb.toString());
		Assert.assertEquals(0, simpleHashtable.size());
	}

}
