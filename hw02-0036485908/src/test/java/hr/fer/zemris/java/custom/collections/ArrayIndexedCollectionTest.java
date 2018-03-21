package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ArrayIndexedCollectionTest {

	ArrayIndexedCollection array;

	@Before
	public void initialize() {
		array = new ArrayIndexedCollection();
	}

	public ArrayIndexedCollection fillArray() {
		array.add("0");
		array.add("First");
		array.add(2);
		array.add("Third");
		array.add("4.12");
		array.add("Fifth");
		return array;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSettingArrayCapacityToZero() {
		new ArrayIndexedCollection(0);
	}

	@Test
	public void testAddRegular() {
		array.add("first");
		array.add("second");
		assertEquals(2, array.size());
	}

	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		array.add(null);
	}

	@Test
	public void testClearRegular() {
		array.clear();
		assertTrue(array.size() == 0);
	}

	@Test
	public void testClearEmptyArray() {
		array.clear();
		assertTrue(array.size() == 0);
	}

	@Test
	public void testContainsRegular() {
		array = fillArray();
		assertTrue(array.contains("First"));
	}

	@Test
	public void testContainsNull() {
		array = fillArray();
		assertFalse(array.contains(null));
	}

	@Test
	public void testContainsEmptyArray() {
		assertFalse(array.contains("First"));
	}

	@Test(expected = NullPointerException.class)
	public void testForEachNull() {
		array.forEach(null);
	}
	
	@Test
	public void testGetRegular() {
		array=fillArray();
		assertEquals("0", array.get(0).toString());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetTooBigIndex() {
		array=fillArray();
		array.get(17);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetEmptyArray() {
		array.get(0);
	}
	
	@Test
	public void testIndexOfRegular() {
		array=fillArray();
		assertEquals(4, array.indexOf("4.12"));
	}
	
	@Test
	public void testIndexOfNull() {
		assertEquals(-1, array.indexOf(null));
	}
	
	@Test
	public void testIndexOfNonExistingElement() {
		assertEquals(-1, array.indexOf("Casper"));
	}
	
	@Test
	public void testInsertEmptyArray() {
		array.insert("Only one", 1);
		assertEquals("Only one", array.get(0).toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void testInsertNull() {
		array.insert(null, 1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testInsertTooBigPosition() {
		array=fillArray();
		array.insert("Too big position", 10);
	}
	
	@Test
	public void testInsertElementsShifting() {
		array=fillArray();
		array.insert("Inserted element", 5);
		
		assertEquals("Third", array.get(3).toString());
		assertEquals("Inserted element", array.get(4).toString());
		assertEquals("4.12", array.get(5).toString());
	}
	
	@Test
	public void testRemoveRegular() {
		array=fillArray();
		Object element = array.get(3);
		array.remove(3);
		assertFalse(array.get(3).equals(element));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveEmptyArray() {
		array.remove(1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testRemoveNegativeIndex() {
		array.remove(-1);
	}
	
	@Test
	public void testSizeRegular() {
		array=fillArray();
		assertEquals(6, array.size());
	}
	
	@Test
	public void testSizeEmptyArray() {
		assertEquals(0, array.size());
	}
	
	@Test
	public void testToArrayRegular() {
		array=fillArray();
		assertArrayEquals(new Object[] {"0", "First", 2, "Third", "4.12", "Fifth"}, array.toArray());
	}
	
	@Test
	public void testToArrayNull() {
		assertArrayEquals(new Object[] {}, array.toArray());
	}
	
	
	

	

}
