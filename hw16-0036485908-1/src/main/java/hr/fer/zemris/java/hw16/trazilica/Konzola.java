package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;
import static hr.fer.zemris.java.hw16.trazilica.Vocabulary.WORD_REGEX;

public class Konzola {
	public static final String QUERY = "query";
	public static final String TYPE = "type";
	public static final String RESULTS = "results";
	public static final String EXIT = "exit";

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
		System.out.println("Veličina rječnika je " + vocabulary.getSize());

		while (true) {
			System.out.print("Enter command > ");
			String input = null;
			if (sc.hasNext()) {
				input = sc.nextLine().trim();
			}

			if (input.startsWith(QUERY)) {
				processQuery(input, vocabulary);

			} else if (input.startsWith(TYPE)) {
				processType(input);

			} else if (input.startsWith(RESULTS)) {
				processResults(input);

			} else if (input.startsWith(EXIT)) {
				sc.close();
				break;

			} else {
				System.out.println("Nepoznata naredba.");
			}

		}

	}

	private static void processResults(String input) {
	}

	private static void processType(String input) {
	}

	private static void processQuery(String input, Vocabulary vocabulary) {
		List<String> stopWords = new ArrayList<>();
		stopWords.addAll(vocabulary.getStopWords());
		List<String> queryList = Arrays.asList(input.split(WORD_REGEX))
									   .stream()
									   .skip(1)
									   .map(String::toLowerCase)
									   .filter(word -> !stopWords.contains(word))
									   .collect(Collectors.toList());

		System.out.println("Query is: " + queryList);
	}

	private static void initializeIDFVector(List<Document> documents, List<String> dictionary, double[] idfVector) {
		int numOfDocuments = documents.size();

		for (int i = 0, size = dictionary.size(); i < size; i++) {
			idfVector[i] = Math.log(numOfDocuments / calculateOccurences(documents, i));
		}
	}

	private static int calculateOccurences(List<Document> documents, int position) {
		int numOfOccurences = 0;

		for (Document document : documents) {
			if (document.getTfVector()[position] > 0) {
				numOfOccurences++;
			}
		}

		return numOfOccurences;
	}

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
