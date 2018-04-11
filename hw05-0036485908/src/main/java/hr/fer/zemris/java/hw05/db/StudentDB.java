package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	public static final String EXIT = "exit";
	public static final String START = "start";
	public static final String GOODBYE_MESSAGE = "Goodbye!";

	private static List<String> lines;
	private static String query = START;
	private static Scanner sc;
	private static QueryParser parser;
	private static StudentDatabase database;
	private static List<StudentRecord> output;

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
			parser.parse();

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

	private static void processCompositeQuery() {
		output = database.filter(new QueryFilter(parser.expressions));
		printOutputStudents(output);
		System.out.println("Records selected: " + output.size());
	}

	private static void processDirectQuery() {
		System.out.println("Using index for record retrieval.");
		output.add(database.forJMBAG(parser.getQueriedJMBAG()));
		printOutputStudents(output);
		System.out.println("Records selected: " + output.size());
	}

	private static void printOutputStudents(List<StudentRecord> output) {
		for (StudentRecord record : output) {
			System.out.println(record.toString());
		}
	}

}
