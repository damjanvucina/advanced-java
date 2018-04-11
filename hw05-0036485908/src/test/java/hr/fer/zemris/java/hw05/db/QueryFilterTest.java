package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryFilterTest {

	StudentDatabase database;
	List<StudentRecord> output;
	QueryParser parser;

	@Before
	public void setUp() {
		try {
			database = new StudentDatabase(Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("Cannot open file.");
		}
	}

	@Test
	public void testFilteringNonDirectQuery() {
		parser = new QueryParser("lastName LIKE \"B*\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(4, output.size());
	}

	@Test
	public void testFilteringDirectQuery() {
		parser = new QueryParser("jmbag = \"0000000003\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(1, output.size());
	}

	@Test
	public void testFilteringCompositeQuery() {
		parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(1, output.size());
	}

	@Test
	public void testFilteringCompositeQueryNoMatches() {
		parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"L*\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(0, output.size());
	}

	@Test
	public void testFilteringNonDirectQueryNoMatches() {
		parser = new QueryParser("lastName LIKE \"Be*\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(0, output.size());
	}

	@Test
	public void testFilteringFirstNameMultipleMatch() {
		parser = new QueryParser("firstName = \"Ivan\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(5, output.size());
	}

	@Test
	public void testFilteringSingleMatch() {
		parser = new QueryParser("firstName = \"Jusufadis\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(1, output.size());
	}

	@Test
	public void testFilteringJmbagGreaterThanMultipleMatch() {
		parser = new QueryParser("jmbag > \"0000000043\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		Assert.assertEquals(20, output.size());
	}
}
