package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {

	StudentDatabase database;

	List<String> testList;
	String text;

	@Before
	public void setUp() {
		try {
			text = new String(Files.readAllBytes(Paths.get("./src/main/resources/database.txt")),
					StandardCharsets.UTF_8);

		} catch (IOException e) {
			System.out.println("An IO exception occured.");
		}

		testList = new LinkedList<>();
		for (String row : text.split("\r\n")) {
			testList.add(row);
		}

		database = new StudentDatabase(testList);
	}

	@Test
	public void testForJmbagTrue() {
		StudentRecord testRecord = new StudentRecord("0000000026", "Zoran", "Katunarić", 3);
		Assert.assertEquals(testRecord, database.forJMBAG("0000000026"));
	}
	
	@Test
	public void testHashcodeAndEquals() {
		StudentRecord testRecord = new StudentRecord("0000000026", "Irrelevant", "Irrelevant", -1);
		Assert.assertEquals(testRecord, database.forJMBAG("0000000026"));
	}
	
	@Test
	public void testForJmbagFalse() {
		Assert.assertEquals(null, database.forJMBAG("Non existing"));
	}
	
	@Test
	public void testFilterTrueReturning() {
		List<StudentRecord> filteredList = database.filter(record -> true); 
		Assert.assertTrue(database.getStudentRecords().equals(filteredList));
	}
	
	@Test
	public void testFilterFalseReturning() {
		testList.clear();
		Assert.assertEquals(testList, database.filter(record -> false));
	}
	
	
}
