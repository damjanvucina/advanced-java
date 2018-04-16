package hr.fer.zemris.java.hw06.observer1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegerStorageTest {
	
	IntegerStorage istorage;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void integrationTest() {
		String expected = "Number of value changes since tracking: 1\r\n" + 
						  "Double value: 8\r\n" + 
						  "Provided new value: 4, square is 16\r\n" + 
						  "Double value: 8\r\n" + 
						  "Double value: 6\r\n" + 
						  "Provided new value: 3, square is 9\r\n";
		
		istorage = new IntegerStorage(3);
		
		ChangeCounter changeCounter = new ChangeCounter();
		istorage.addObserver(changeCounter);
		
		istorage.setValue(3);
		istorage.setValue(4);
		istorage.setValue(4);
		
		istorage.removeObserver(changeCounter);
		
		istorage.setValue(5);
		
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new DoubleValue(1));
		
		istorage.setValue(5);
		istorage.setValue(4);
		istorage.setValue(3);
		
		Assert.assertEquals(expected, outContent.toString());
	}
	
	@Test
	public void templateTest() {
		String expected = "Provided new value: 5, square is 25\r\n" + 
						  "Provided new value: 2, square is 4\r\n" + 
						  "Provided new value: 25, square is 625\r\n" + 
						  "Number of value changes since tracking: 1\r\n" + 
						  "Double value: 26\r\n" + 
						  "Double value: 26\r\n" + 
						  "Double value: 26\r\n" + 
						  "Number of value changes since tracking: 2\r\n" + 
						  "Double value: 44\r\n" + 
						  "Double value: 44\r\n" + 
						  "Number of value changes since tracking: 3\r\n";
		
		istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
		Assert.assertEquals(expected, outContent.toString());
	}

}
