package hr.fer.zemris.java.gui.prim;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrimListModelTest {
	
	PrimListModel model;
	
	@Before
	public void setUp() {
		model = new PrimListModel();
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullListener() {
		model.addListDataListener(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getElementAtInvalidIndex() {
		model.getElementAt(1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getElementAtNegativeIndex() {
		model.getElementAt(-1);
	}
	
	@Test
	public void getDefaultSize() {
		Assert.assertEquals(1, model.getSize());
	}
	
	@Test(expected = NullPointerException.class)
	public void removeNullListener() {
		model.removeListDataListener(null);
	}
	
	@Test
	public void testFirstFewPrimes() {
		model.next();
		Assert.assertEquals(Integer.valueOf(2), model.getElementAt(model.getSize()-1));
		model.next();
		Assert.assertEquals(Integer.valueOf(3), model.getElementAt(model.getSize()-1));
		model.next();
		Assert.assertEquals(Integer.valueOf(5), model.getElementAt(model.getSize()-1));
		model.next();
		Assert.assertEquals(Integer.valueOf(7), model.getElementAt(model.getSize()-1));
		model.next();
		Assert.assertEquals(Integer.valueOf(11), model.getElementAt(model.getSize()-1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isPrimeNegative() {
		Assert.assertFalse(model.isPrime(-1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void isPrimeZero() {
		Assert.assertFalse(model.isPrime(0));
	}
	
	@Test
	public void isPrimeTrue() {
		Assert.assertTrue(model.isPrime(13));
	}
	
	@Test
	public void isPrimeFalse() {
		Assert.assertFalse(model.isPrime(99));
	}

}
