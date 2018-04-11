package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class StudentDB {
	public static final String START = "start";
	public static final String EXIT = "exit";
	public static final String GOODBYE_MESSAGE = "Goodbye!";

	private static final int MARGIN_LENGTH = 2;
	public static final int GRADE_COLUMN_LENGTH = 3;
	public static final int JMBAG_COLUMN_LENGTH = 12;

	public static final String PADDING_SYMBOL = "=";
	public static final String COLUMN_SPLIT_SYMBOL = "+";
	private static final String WHITESPACE = " ";

	private static List<String> lines;
	private static List<StudentRecord> output;
	private static StudentDatabase database;
	private static String query = START;
	private static QueryParser parser;
	private static Scanner sc;

	private static int lastNameColumnLength;
	private static int firstNameColumnLength;

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

			System.out.print("> query ");

			query = sc.nextLine().trim();

			if (query.equals("exit")) {
				break;
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

	private static String appendSymbol(String symbol, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(symbol);
		}

		return sb.toString();
	}
}
