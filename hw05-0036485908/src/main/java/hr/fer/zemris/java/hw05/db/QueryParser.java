package hr.fer.zemris.java.hw05.db;

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

	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	public void setQuery(String query) {
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
			return stringLiteral;

		} else if (attribute.equals("jmbag")) {
			return stringLiteral;
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
}
