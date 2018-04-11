package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

/**
 * The parser that is used for iterating over the input query and constructing
 * the list of the conditional expressions from that query. A conditional
 * expression is a condition that a specific element of this database can but
 * does not have to fulfill.
 * 
 * @author Damjan Vučina
 */
public class QueryParser {

	/** The Constant VALID_JMBAG, i.e. regex that represents a 10 digit number. */
	private static final String VALID_JMBAG = "\\d{10}";

	/**
	 * The Constant QUERY_SPLITTER, i.e. regex that represents a splitter between
	 * multiple conditional expressions. A conditional expression is a condition
	 * that a specific element of this database can but does not have to fulfill.
	 */
	private static final String QUERY_SPLITTER = "(?i:AND)";

	/** The input query. */
	String query;

	/**
	 * The list of the conditional expressions constructed from the input query. A
	 * conditional expression is a condition that a specific element of this
	 * database can but does not have to fulfill.
	 */
	List<ConditionalExpression> expressions;

	/**
	 * Instantiates a new query parser.
	 */
	public QueryParser() {
		expressions = new LinkedList<>();
	}

	/**
	 * Instantiates a new query parser.
	 *
	 * @param query
	 *            the input query
	 */
	public QueryParser(String query) {
		this();
		this.query = query;
	}

	/**
	 * Gets the list of the conditional expressions constructed from the input
	 * query. A conditional expression is a condition that a specific element of
	 * this database can but does not have to fulfill..
	 *
	 * @return the query
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Sets the new query for the parser to process.
	 *
	 * @param query
	 *            the new query for the parser to process.
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Checks if a query is direct, meaning if query is of of the form jmbag="xxx"
	 * (i.e. it must have only one comparison, on attribute jmbag, and operator must
	 * be equals).
	 *
	 * @return true, if the last performed query is direct query
	 */
	public boolean isDirectQuery() {
		String queryCopy = query.replace(" ", "");

		if (queryCopy.startsWith("jmbag=")) {
			queryCopy = queryCopy.substring("jmbag=".length() + 1, queryCopy.length() - 1);

			return queryCopy.matches(VALID_JMBAG);

		}
		return false;
	}

	/**
	 * Gets the queried JMBAG. Method can be invoked only if the last performed
	 * query was a direct query, meaning if query is of of the form jmbag="xxx"
	 * (i.e. it must have only one comparison, on attribute jmbag, and operator must
	 * be equals)..
	 *
	 * @return the queried JMBAG
	 * 
	 * @throws IllegalStateException if last performed query was not a direct query.
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Last performed query was not a direct query.");
		}
		String queryCopy = query.replace(" ", "");

		return queryCopy.substring("jmbag=\"".length(), queryCopy.length() - 1);

	}

	/**
	 * Parses the input query.
	 */
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

	/**
	 * Parses the attribute of the input query.
	 *
	 * @param queryCopy
	 *            the copy of the original query
	 * @param attribute
	 *            the attribute that is currently being parsed
	 */
	private void parseAttribute(String queryCopy, String attribute) {
		IComparisonOperator comparisonOperator = validateComparisonOperator(
				queryCopy.substring(attribute.length(), queryCopy.indexOf("\"")));
		IFieldValueGetter fieldGetter = validateFieldGetter(attribute);
		String stringLiteral = validateStringLiteral(
				queryCopy.substring(queryCopy.indexOf("\"") + 1, queryCopy.lastIndexOf("\"")), attribute);

		expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
	}

	/**
	 * Validates the string literal.
	 *
	 * @param stringLiteral
	 *            the string literal of an element of this database
	 * @param attribute
	 *            the attribute of an element of this database
	 * @return the string
	 * @throws IllegaArgumentException if invalid string literal was provided
	 */
	private String validateStringLiteral(String stringLiteral, String attribute) {
		if (attribute.equals("firstName") || attribute.equals("lastName")) {
			return stringLiteral;

		} else if (attribute.equals("jmbag")) {
			return stringLiteral;
		}

		throw new IllegalArgumentException("Invalid stringLiteral, was: " + stringLiteral);
	}

	/**
	 * Validates the field getter.
	 *
	 * @param attribute
	 *             the attribute of an element of this database
	 * @return an instance of an IFieldValueGetter
	 * @throws IllegaArgumentException if invalid field getter was provided
	 */
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

	/**
	 * Validates the comparison operator.
	 *
	 * @param comparisonOperator
	 *            the comparison operator between two elements of this database
	 * @return an instance of the IComparisonOperator
	 * @throws IllegaArgumentException if unsupported operator was provided
	 */
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
