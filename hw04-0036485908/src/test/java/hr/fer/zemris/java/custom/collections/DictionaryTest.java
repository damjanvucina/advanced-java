package hr.fer.zemris.java.custom.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;

public class DictionaryTest {

	Dictionary dictionary;

	@Before
	public void initialize() {
		dictionary = new Dictionary();
	}

	public Dictionary fillDictionary() {
		dictionary.put(1, "First");
		dictionary.put(2, "Second");
		dictionary.put("Three", "Third");
		dictionary.put("Four", "Fourth");

		return dictionary;
	}

	@Test
	public void isEmptyTest() {
		Assert.assertEquals(true, dictionary.isEmpty());
	}

	@Test
	public void sizeEmptyTest() {
		Assert.assertEquals(0, dictionary.size());
	}

	@Test
	public void sizeRegularTest() {
		dictionary = fillDictionary();
		Assert.assertEquals(4, dictionary.size());
	}

	@Test
	public void clearEmptyTest() {
		dictionary.clear();
		Assert.assertEquals(0, dictionary.size());
	}

	@Test
	public void clearRegularTest() {
		dictionary = fillDictionary();
		dictionary.clear();
		Assert.assertEquals(0, dictionary.size());
	}

	@Test
	public void putNewElementTest() {
		dictionary = fillDictionary();
		dictionary.put(5, "Fifth");
		Assert.assertEquals(5, dictionary.size());
	}

	@Test
	public void rewriteExistingTest() {
		dictionary = fillDictionary();
		dictionary.put(1, "First");
		Assert.assertEquals(4, dictionary.size());
	}

	@Test
	public void rewriteExistingSameKeyDifferentValueTest() {
		dictionary = fillDictionary();
		dictionary.put(1, "Still Rewriting");
		Assert.assertEquals(4, dictionary.size());
	}

	@Test
	public void getRegularTest() {
		dictionary = fillDictionary();
		Assert.assertEquals(String.valueOf("First"), dictionary.get(1).toString());
	}
	
	@Test
	public void getEmptyTest() {
		assertEquals(null, dictionary.get("I don't exist"));
	}
}
