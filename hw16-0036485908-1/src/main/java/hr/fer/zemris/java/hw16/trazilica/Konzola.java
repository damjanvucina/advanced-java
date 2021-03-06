package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;
import static hr.fer.zemris.java.hw16.trazilica.Vocabulary.WORD_REGEX;

/**
 * Demonstration class that emulates a simple shell for communicating with the
 * user. Responisble for performin initialization of the vocabulary, tf vector
 * as well as idf vector.This class provides the user with the ability to find
 * the most similar documents to the entered query.
 * 
 * Supported commands:
 * 
 * 1) QUERY (query word1 word2 ... wordn) Transforms user's query into a vector
 * representation and prints most similar documents
 * 
 * 2) TYPE (type n) Obtains ordinal number of the document previously returned
 * by the query command and prints its contents to the console
 * 
 * 3) RESULTS (results) Prints most similar documents to the console based on
 * the query that has previously been entered.
 * 
 * $) EXIT (exit) Exits this program.
 * 
 * 
 * @author Damjan Vučina
 */
public class Konzola {

	/** The Constant QUERY. */
	public static final String QUERY = "query";

	/** The Constant TYPE. */
	public static final String TYPE = "type";

	/** The Constant RESULTS. */
	public static final String RESULTS = "results";

	/** The Constant EXIT. */
	public static final String EXIT = "exit";

	/**
	 * The main method. Recives an argument that represent a path to the folder
	 * containing the txt files used to build the vocabulary.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException(
					"You must provide a single path argument; arguments provided: " + args.length);
		}

		Path directory = Paths.get(args[0]);

		if (Files.notExists(directory) || !Files.isReadable(directory)) {
			throw new IllegalArgumentException("Cannot access folder.");
		}

		Vocabulary vocabulary = new Vocabulary();
		initializeVocabulary(directory, vocabulary);
		vocabulary.generateList();

		List<Document> documents = new ArrayList<>();
		initalizeTFVectors(directory, documents, vocabulary);

		double[] idfVector = new double[vocabulary.getList().size()];
		List<String> dictionary = vocabulary.getList();
		initializeIDFVector(documents, dictionary, idfVector);

		Scanner sc = new Scanner(System.in);
		Map<Double, String> results = null;
		System.out.println("Veličina rječnika je " + vocabulary.getSize());

		while (true) {
			System.out.print("Enter command > ");
			String input = null;
			if (sc.hasNext()) {
				input = sc.nextLine().trim();
			}

			if (input.startsWith(QUERY)) {
				results = processQuery(input, vocabulary, documents, idfVector, results);

			} else if (input.startsWith(TYPE)) {
				processType(input, results);

			} else if (input.equals(RESULTS)) {
				processResults(results);

			} else if (input.equals(EXIT)) {
				sc.close();
				break;

			} else {
				System.out.println("Nepoznata naredba.");
			}

		}

	}

	/**
	 * Processes the results command.
	 * 
	 * RESULTS (results) Prints most similar documents to the console based on the
	 * query that has previously been entered.
	 *
	 * @param results
	 *            the results
	 */
	private static void processResults(Map<Double, String> results) {
		if (results == null) {
			System.out.println("Nije moguće ispisati rezultate prije izvršavanja upita");
			return;
		}

		printResults(results);
	}

	/**
	 * Processes the type comand.
	 * 
	 * TYPE (type n) Obtains ordinal number of the document previously returned by
	 * the query command and prints its contents to the console
	 *
	 * @param input
	 *            the input
	 * @param results
	 *            the results
	 */
	private static void processType(String input, Map<Double, String> results) {
		if (results == null) {
			System.out.println("Nije moguće ispisati rezultate prije izvršavanja upita");
			return;
		}

		int choice = 0;

		try {
			choice = Integer.parseInt(input.split(" ")[1]);
		} catch (NumberFormatException e) {
			System.out.println("Pogrešno formatirana naredba.");
			return;
		}

		if (choice > results.size()) {
			System.out.println("Mapa ima samo " + results.size() + " elemenata.");
			return;
		}

		String result = results.entrySet().stream().skip(choice).findFirst().get().getValue();
		System.out.println("Dokument " + result);
		System.out.println();

		try {
			Files.newBufferedReader(Paths.get(result), StandardCharsets.UTF_8).lines()
					.forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the query command.
	 * 
	 * QUERY (query word1 word2 ... wordn) Transforms user's query into a vector
	 * representation and prints most similar documents
	 *
	 * @param input
	 *            the user's input
	 * @param vocabulary
	 *            the reference to the used vocabulary
	 * @param documents
	 *            the list of the documents
	 * @param idfVector
	 *            the idf vector
	 * @param results
	 *            the results
	 * @return the map
	 */
	//@formatter:off
	private static Map<Double, String> processQuery(String input, Vocabulary vocabulary,
									 List<Document> documents, double[] idfVector,
									 Map<Double, String> results) {

		List<String> stopWords = new ArrayList<>();
		stopWords.addAll(vocabulary.getStopWords());
		List<String> queryList = Arrays.asList(input.split(WORD_REGEX))
									   .stream()
									   .skip(1)
									   .map(String::toLowerCase)
									   .filter(word -> !stopWords.contains(word))
									   .collect(Collectors.toList());
		
		Document queryDocument = new Document(vocabulary, queryList);
		results = Document.calculateSimilarity(documents, queryDocument, idfVector);
		
		System.out.println("Query is: " + queryList);
		System.out.println("Najboljih 10 rezultata:");
		
		printResults(results);
		
		return results;
	}
	
	/**
	 * Prints the results of the query command.
	 *
	 * @param results the results
	 */
	private static void printResults(Map<Double, String> results) {
		int counter = 0;
		for(Entry<Double, String> entry : results.entrySet()) {
			System.out.print("[" + counter + "] ");
			System.out.printf("(%.4f) ", entry.getKey());
			System.out.println(entry.getValue());
			
			counter++;
		}
	}

	//@formatter:on

	/**
	 * Initializes IDF vector based on the files that are used to build the
	 * vocabulary.
	 *
	 * @param documents
	 *            the documents
	 * @param dictionary
	 *            the dictionary
	 * @param idfVector
	 *            the idf vector
	 */
	private static void initializeIDFVector(List<Document> documents, List<String> dictionary, double[] idfVector) {
		int numOfDocuments = documents.size();

		for (int i = 0, size = dictionary.size(); i < size; i++) {
			idfVector[i] = Math.log(numOfDocuments * 1.0 / calculateOccurences(documents, i));
		}
	}

	/**
	 * Calculates occurences of each word in all of the documents.
	 *
	 * @param documents
	 *            the documents
	 * @param position
	 *            the position
	 * @return the int
	 */
	private static int calculateOccurences(List<Document> documents, int position) {
		int numOfOccurences = 0;

		for (Document document : documents) {
			if (document.getTfVector()[position] > 0) {
				numOfOccurences++;
			}
		}

		return numOfOccurences;
	}

	/**
	 * Initalizes term frequency vectors.
	 *
	 * @param directory
	 *            the directory
	 * @param documents
	 *            the documents
	 * @param vocabulary
	 *            the vocabulary
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void initalizeTFVectors(Path directory, List<Document> documents, Vocabulary vocabulary)
			throws IOException {
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				documents.add(new Document(file, vocabulary));

				return CONTINUE;
			}
		});
	}

	/**
	 * Initializes the vocabulary.
	 *
	 * @param directory
	 *            the directory
	 * @param vocabulary
	 *            the vocabulary
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void initializeVocabulary(Path directory, Vocabulary vocabulary) throws IOException {
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				vocabulary.fillFromFile(file);

				return CONTINUE;
			}
		});
	}
}
