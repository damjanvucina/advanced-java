package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The program reads the data from current directory from file prva.txt, and
 * provides methods for interaction with user via console. This program supports
 * two commands: exit and query. Exit command is used for closing the program.
 * Query command is used for specified a certain query and a request for parsing
 * to the program. The program parses the given query and returns the result in
 * a form of a table consisting of student records whose attributes are in
 * conformance with the specified query if there are any. Program supports
 * following seven comparison operators: >, <, >=, <=, =, !=, LIKE. Command,
 * attribute name, operator, string literal and logical operator AND can be
 * separated by more than one tabs or spaces. However, space is not needed
 * between atribute and operator, and between operator and string literal.
 * Logical operator AND can be written with any casing: AND, and, AnD etc is OK.
 * Filtering expressions are built using only jmbag, lastName and firstName
 * attributes. No other attributes are allowed in query. If more than one
 * expression is given, all of them must be composed by logical AND
 * operator.Command names, attribute names and literals are case sensitive. If
 * query is given only a single attribute (which must be jmbag) and a comparison
 * operator is =, the command obtains the requested student using the indexing
 * facility of database implementation in O(1) complexity.*
 * 
 * @author Damjan Vučina
 */
public class StudentDB {

	/** The Constant QUERY that represents a parsed query. */
	public static final String QUERY = "query";

	/** The Constant START that represents the initial state of the parser. */
	public static final String START = "start";

	/** The Constant EXIT that represents the end state of the parser.. */
	public static final String EXIT = "exit";

	/**
	 * The Constant GOODBYE_MESSAGE displayed to the user after entering exit
	 * command.
	 */
	public static final String GOODBYE_MESSAGE = "Goodbye!";

	/**
	 * The Constant MARGIN_LENGTH that represent the length of the margin of the
	 * output table of students.
	 */
	private static final int MARGIN_LENGTH = 2;

	/**
	 * The Constant GRADE_COLUMN_LENGTH that represent the length of the grade
	 * column of the output table of students.
	 */
	public static final int GRADE_COLUMN_LENGTH = 3;

	/**
	 * The Constant JMBAG_COLUMN_LENGTH that represent the length of the jmbag
	 * column of the output table of students.
	 */
	public static final int JMBAG_COLUMN_LENGTH = 12;

	/**
	 * The Constant PADDING_SYMBOL that represent a padding symbol of the output
	 * table of students.
	 */
	public static final String PADDING_SYMBOL = "=";

	/**
	 * The Constant COLUMN_SPLIT_SYMBOL that represent a column split symbol of the
	 * output table of students..
	 */
	public static final String COLUMN_SPLIT_SYMBOL = "+";

	/** The Constant WHITESPACE. */
	private static final String WHITESPACE = " ";

	/** The String representation of the input text file. */
	private static List<String> lines;

	/**
	 * The List of student records whose attributes are in conformance with the
	 * specified query if there are any.
	 */
	private static List<StudentRecord> output;

	/** The database of students. */
	private static StudentDatabase database;

	/** The input query. */
	private static String query = START;

	/** The parser that parses the input query. */
	private static QueryParser parser;

	/** The scanner used for reading from the console. */
	private static Scanner sc;

	/**
	 * The length of the last name column in output table of student records whose
	 * attributes are in conformance with the specified query.
	 */
	private static int lastNameColumnLength;

	/**
	 * The length of the first name column in output table of student records whose
	 * attributes are in conformance with the specified query
	 */
	private static int firstNameColumnLength;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		try {
			lines = Files.readAllLines(Paths.get("./prva.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Requested file does not exist");
		}

		database = new StudentDatabase(lines);
		sc = new Scanner(System.in);
		parser = new QueryParser();
		output = new LinkedList<>();

		while (!query.equals(EXIT)) {
			output.clear();
			parser.expressions.clear();

			System.out.print("> ");

			query = sc.nextLine().trim();

			if (query.equals("exit")) {
				break;
			}

			if (!query.startsWith(QUERY)) {
				System.out.println("Valid commands are exit and query" + "\n");
				continue;
			} else {
				query = query.substring(QUERY.length());
			}

			parser.setQuery(query.trim());

			try {
				parser.parse();
			} catch (IllegalStateException | IllegalArgumentException | StringIndexOutOfBoundsException ex) {
				System.out.println(ex.getMessage() + "\n");
				continue;
			}

			if (parser.isDirectQuery()) {
				processDirectQuery();
			} else {
				processCompositeQuery();
			}
			System.out.println();
		}

		System.out.println(GOODBYE_MESSAGE);
		sc.close();

	}

	/**
	 * Processes the direct query, meaning if query is of of the form jmbag="xxx"
	 * (i.e. it must have only one comparison, on attribute jmbag, and operator must
	 * be equals)..
	 */
	private static void processDirectQuery() {
		System.out.println("Using index for record retrieval.");
		output.add(database.forJMBAG(parser.getQueriedJMBAG()));

		firstNameColumnLength = setColumnLength(record -> record.getFirstName().length());
		lastNameColumnLength = setColumnLength(record -> record.getLastName().length());

		if (output.size() != 0) {
			printPadding();
			printOutputStudents(output);
			printPadding();
		}

		System.out.println("Records selected: " + output.size());
	}

	/**
	 * Process composite query, meaning that the query is not a direct query. Direct
	 * query is of of the form jmbag="xxx" (i.e. it must have only one comparison,
	 * on attribute jmbag, and operator must be equals). Every other query is
	 * composite query.
	 */
	private static void processCompositeQuery() {
		output = database.filter(new QueryFilter(parser.expressions));

		firstNameColumnLength = setColumnLength(record -> record.getFirstName().length());
		lastNameColumnLength = setColumnLength(record -> record.getLastName().length());

		if (output.size() != 0) {
			printPadding();
			printOutputStudents(output);
			printPadding();
		}

		System.out.println("Records selected: " + output.size());
	}

	/**
	 * Prints the padding to the output table of student records whose attributes
	 * are in conformance with the specified query..
	 */
	private static void printPadding() {
		StringBuilder sb = new StringBuilder();

		sb.append(COLUMN_SPLIT_SYMBOL);
		sb.append(appendSymbol(PADDING_SYMBOL, JMBAG_COLUMN_LENGTH));
		sb.append(COLUMN_SPLIT_SYMBOL);
		sb.append(appendSymbol(PADDING_SYMBOL, lastNameColumnLength + MARGIN_LENGTH));
		sb.append(COLUMN_SPLIT_SYMBOL);
		sb.append(appendSymbol(PADDING_SYMBOL, firstNameColumnLength + MARGIN_LENGTH));
		sb.append(COLUMN_SPLIT_SYMBOL);
		sb.append(appendSymbol(PADDING_SYMBOL, GRADE_COLUMN_LENGTH));
		sb.append(COLUMN_SPLIT_SYMBOL);

		System.out.println(sb.toString());
	}

	/**
	 * Prints the student records to the output table of student records whose
	 * attributes are in conformance with the specified query.
	 *
	 * @param output
	 *            the output
	 */
	private static void printOutputStudents(List<StudentRecord> output) {
		StringBuilder sb = new StringBuilder();
		for (StudentRecord record : output) {

			sb.append("| ").append(record.getJmbag()).append(" | ").append(record.getLastName());
			sb.append(appendSymbol(WHITESPACE, lastNameColumnLength - record.getLastName().length()));

			sb.append(" | ").append(record.getFirstName());
			sb.append(appendSymbol(WHITESPACE, firstNameColumnLength - record.getFirstName().length()));

			sb.append(" | ").append(record.getFinalGrade()).append(" |").append("\n");
		}
		sb.delete(sb.lastIndexOf("\n"), sb.length());

		System.out.println(sb.toString());
	}

	/**
	 * Sets the length of the column in output table of student records whose
	 * attributes are in conformance with the specified query.
	 *
	 * @param action
	 *            the action
	 * @return the int length of the column
	 */
	private static int setColumnLength(Function<StudentRecord, Integer> action) {
		int currentMax = 0;
		int currentLength = 0;
		for (StudentRecord record : output) {
			currentLength = action.apply(record);
			if (currentLength > currentMax) {
				currentMax = currentLength;
			}
		}
		return currentMax;

	}

	/**
	 * Helper method which appends the specified symbols specified number of times
	 * and return such string.
	 *
	 * @param symbol
	 *            the symbol appended
	 * @param length
	 *            the number of times the symbol has to be appended
	 * @return the string result of this operation
	 */
	private static String appendSymbol(String symbol, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(symbol);
		}

		return sb.toString();
	}
}
