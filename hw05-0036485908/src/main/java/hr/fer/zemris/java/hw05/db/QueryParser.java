package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class QueryParser {
	private static final String VALID_JMBAG = "\\d{10}";
	private static final String QUERY_SPLITTER = "(?i:AND)";

	String query;
	List<ConditionalExpression> expressions;
	
	public QueryParser() {
		expressions = new LinkedList<>();
	}

	public QueryParser(String query) {
		this();
		this.query = query;
	}

	public boolean isDirectQuery() {
		String queryCopy = query.replace(" ", "");

		if (queryCopy.startsWith("jmbag=")) {
			queryCopy = queryCopy.substring("jmbag=".length() + 1, queryCopy.length() - 1);

			return queryCopy.matches(VALID_JMBAG);

		}
		return false;
	}

	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Last performed query was not a direct query.");
		}
		String queryCopy = query.replace(" ", "");

		return queryCopy.substring("jmbag=\"".length(), queryCopy.length() - 1);

	}

	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void parse() {
		String queryCopy = query.replace(" ", "");
		String[] queries = queryCopy.split(QUERY_SPLITTER);

		for (String singleQuery : queries) {
			if (singleQuery.startsWith("firstName")) {
				parseAttribute(singleQuery, "firstName");

			} else if (singleQuery.startsWith("lastName")) {
				parseAttribute(singleQuery, "lastName");

			} else if (singleQuery.startsWith("jmbag")) {
				parseAttribute(singleQuery, "jmbag");

			} else {
				throw new IllegalArgumentException("Unsupported query operation.");
			}
		}
	}

	private void parseAttribute(String queryCopy, String attribute) {
		IComparisonOperator comparisonOperator = validateComparisonOperator(
				queryCopy.substring(attribute.length(), queryCopy.indexOf("\"")));
		IFieldValueGetter fieldGetter = validateFieldGetter(attribute);
		String stringLiteral = validateStringLiteral(
				queryCopy.substring(queryCopy.indexOf("\"") + 1, queryCopy.lastIndexOf("\"")), attribute);

		expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
	}

	private String validateStringLiteral(String stringLiteral, String attribute) {
		if (attribute.equals("firstName") || attribute.equals("lastName")) {
			//if (stringLiteral.matches(VALID_NAME)) {
				return stringLiteral;
			//}

		} else if (attribute.equals("jmbag")) {
			//if (stringLiteral.matches(VALID_JMBAG)) {
				return stringLiteral;
			//}
		}

		throw new IllegalArgumentException("Invalid stringLiteral, was: " + stringLiteral);
	}

	private IFieldValueGetter validateFieldGetter(String attribute) {
		switch (attribute) {
		case "firstName":
			return FieldValueGetters.FIRST_NAME;

		case "lastName":
			return FieldValueGetters.LAST_NAME;

		case "jmbag":
			return FieldValueGetters.JMBAG;

		default:
			throw new IllegalArgumentException("Unsupported field getter, was: " + attribute);
		}
	}

	private IComparisonOperator validateComparisonOperator(String comparisonOperator) {
		switch (comparisonOperator) {
		case "<":
			return ComparisonOperators.LESS;

		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;

		case ">":
			return ComparisonOperators.GREATER;

		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;

		case "=":
			return ComparisonOperators.EQUALS;

		case "!=":
			return ComparisonOperators.NOT_EQUALS;

		case "LIKE":
			return ComparisonOperators.LIKE;

		default:
			throw new IllegalArgumentException("Unsupported operator occured, was: " + comparisonOperator);
		}
	}
	
	public static void main(String[] args) {
		StudentDatabase database = null;
		List<StudentRecord> output;
		try {
			database = new StudentDatabase(Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("Cannot open file.");
		}
		QueryParser parser = new QueryParser("lastName LIKE \"B*\"");
		parser.parse();
		output = database.filter(new QueryFilter(parser.expressions));
		System.out.println(output.size());
		for (StudentRecord record : output) {
			System.out.println(record.toString());
		}
		System.out.println("Records selected: " + output.size());
	}
}
















