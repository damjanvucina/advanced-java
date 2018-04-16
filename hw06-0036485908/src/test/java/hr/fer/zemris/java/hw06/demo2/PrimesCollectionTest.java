package hr.fer.zemris.java.hw06.demo2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrimesCollectionTest {
	
	PrimesCollection primesCollection;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void regularTest() {
		String expected = "2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71\r\n";
		primesCollection = new PrimesCollection(20);
		System.out.println(primesCollection);
		
		Assert.assertEquals(expected, outContent.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void zeroSizePrimeCollection() {
		primesCollection = new PrimesCollection(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeSizePrimeCollection() {
		primesCollection = new PrimesCollection(-3);
	}
	
	@Test
	public void templateTest1() {
		String expected = "Got prime: 2\r\n" + 
						  "Got prime: 3\r\n" + 
						  "Got prime: 5\r\n" + 
						  "Got prime: 7\r\n" + 
						  "Got prime: 11\r\n";
		
		primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
		
		Assert.assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void templateTest2() {
		String expected = "Got prime pair: 2, 2\r\n" + 
						  "Got prime pair: 2, 3\r\n" + 
						  "Got prime pair: 3, 2\r\n" + 
						  "Got prime pair: 3, 3\r\n";
		
		primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
					System.out.println("Got prime pair: "+prime+", "+prime2);
			}
		}
		
		Assert.assertEquals(expected, outContent.toString());
	}

}
