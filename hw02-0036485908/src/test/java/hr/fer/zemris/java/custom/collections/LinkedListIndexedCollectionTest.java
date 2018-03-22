package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection list;

	@Before
	public void initialize() {
		list = new LinkedListIndexedCollection();
	}

	public LinkedListIndexedCollection fillList() {
		list.add("0");
		list.add("First");
		list.add(2);
		list.add("Third");
		list.add("4.12");
		list.add("Fifth");
		return list;
	}

	@Test
	public void testAddRegular() {
		list.add("first");
		list.add("second");
		assertEquals(2, list.size());
	}

	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		list.add(null);
	}

	@Test
	public void testClearRegular() {
		list.clear();
		assertTrue(list.size() == 0);
	}

	@Test
	public void testClearEmptyList() {
		list.clear();
		assertTrue(list.size() == 0);
	}

	@Test
	public void testContainsRegular() {
		list = fillList();
		assertTrue(list.contains("First"));
	}

	@Test
	public void testContainsNull() {
		list = fillList();
		assertFalse(list.contains(null));
	}

	@Test
	public void testContainsEmptyList() {
		assertFalse(list.contains("First"));
	}

	@Test(expected = NullPointerException.class)
	public void testForEachNull() {
		list.forEach(null);
	}
	
	@Test
	public void testGetRegular() {
		list=fillList();
		assertEquals("0", list.get(0).toString());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetTooBigIndex() {
		list=fillList();
		list.get(17);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetEmptyList() {
		list.get(0);
	}
	
	@Test
	public void testIndexOfRegular() {
		list=fillList();
		assertEquals(4, list.indexOf("4.12"));
	}
	
	@Test
	public void testIndexOfNull() {
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	public void testIndexOfNonExistingElement() {
		assertEquals(-1, list.indexOf("Casper"));
	}
	
	@Test
	public void testInsertEmptyList() {
		list.insert("Only one", 1);
		assertEquals("Only one", list.get(0).toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void testInsertNull() {
		list.insert(null, 1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testInsertTooBigPosition() {
		list=fillList();
		list.insert("Too big position", 10);
	}
	
	@Test
	public void testInsertElementsShifting() {
		list=fillList();
		list.insert("Inserted element", 5);
		
		assertEquals("Third", list.get(3).toString());
		assertEquals("Inserted element", list.get(4).toString());
		assertEquals("4.12", list.get(5).toString());
	}
	
	@Test
	public void testRemoveRegular() {
		list=fillList();
		Object element = list.get(3);
		list.remove(3);
		assertFalse(list.get(3).equals(element));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveEmptyList() {
		list.remove(1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveNegativeIndex() {
		list.remove(-1);
	}
	
	@Test
	public void testSizeRegular() {
		list=fillList();
		assertEquals(6, list.size());
	}
	
	@Test
	public void testSizeEmptyList() {
		assertEquals(0, list.size());
	}
	
	@Test
	public void testToArrayRegular() {
		list=fillList();
		assertArrayEquals(new Object[] {"0", "First", 2, "Third", "4.12", "Fifth"}, list.toArray());
	}
	
	@Test
	public void testToArrayNull() {
		assertArrayEquals(new Object[] {}, list.toArray());
	}
	
}
