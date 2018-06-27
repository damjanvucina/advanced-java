package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import static hr.fer.zemris.java.hw16.trazilica.Vocabulary.WORD_REGEX;

/**
 * The class that represents a single document defined by its path, a
 * term-frequency vector and a vocabulary upon which the term-frequency vector
 * is generated.
 * 
 * @author Damjan Vučina
 */
public class Document {

	/** The Constant NO_SIMILARITY. */
	private static final double NO_SIMILARITY = 0.0;

	/** The path to the document. */
	private Path path;

	/** The term frequency vector. */
	private double[] tfVector;

	/** The vocabulary. */
	private Vocabulary vocabulary;

	/**
	 * Instantiates a new document.
	 *
	 * @param path
	 *            the path to the document
	 * @param vocabulary
	 *            the vocabulary upon which the term frequency vector of this
	 *            document is built
	 */
	public Document(Path path, Vocabulary vocabulary) {
		this.path = path;
		this.vocabulary = vocabulary;
		tfVector = new double[vocabulary.getSize()];

		initializeTFVector();
	}

	/**
	 * Instantiates a new document by delegating to a helper method responsible for
	 * generating a term frequency vector for this document.
	 *
	 * @param vocabulary
	 *            the vocabulary upon which the term frequency vector of this
	 *            document is built
	 * @param queryList
	 *            the query list
	 */
	public Document(Vocabulary vocabulary, List<String> queryList) {
		this.vocabulary = vocabulary;
		tfVector = new double[vocabulary.getSize()];

		processInitialization(queryList);
	}

	/**
	 * Initializes TF vector by delegating to a helper method responsible for
	 * acquring all words from this document, removing unknown words as well as stop
	 * words and generating a term frequency vector.
	 */
	private void initializeTFVector() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		processInitialization(lines);
	}

	/**
	 * Acquires all words from this document, removes unknown words as well as stop
	 * words and generates a term frequency vector.
	 *
	 * @param lines
	 *            the lines
	 */
	//@formatter:off
	private void processInitialization(List<String> lines) {
		List<String> words = new ArrayList<>();
		for (String line : lines) {
			words.addAll(Arrays.asList(line.split(WORD_REGEX))
							   .stream()
							   .map(String::toLowerCase)
							   .collect(Collectors.toList()));
		}

		words.removeAll(vocabulary.getStopWords());
		List<String> dictionary = vocabulary.getList();
		for (String word : words) {
			tfVector[dictionary.indexOf(word)]++;
		}
	}
	//@formatter:on

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gets the tf vector.
	 *
	 * @return the tf vector
	 */
	public double[] getTfVector() {
		return tfVector;
	}

	/**
	 * Calculate similarity.
	 *
	 * @param documents
	 *            the documents
	 * @param queryDocument
	 *            the query document
	 * @param idfVector
	 *            the idf vector
	 * @return the map
	 */
	public static Map<Double, String> calculateSimilarity(List<Document> documents, Document queryDocument,
			double[] idfVector) {

		Map<Double, String> results = new TreeMap<>(Collections.reverseOrder());
		double[] queryTfidf = queryDocument.generateTfIdf(idfVector);

		for (Document document : documents) {
			double[] docTfidf = document.generateTfIdf(idfVector);
			double similarity = cosineSimilarity(queryTfidf, docTfidf);
			results.put(similarity, String.valueOf(document.getPath()));
		}

		results.remove(NO_SIMILARITY);
		return results;
	}

	/**
	 * Calculates the cosine similarity between two vectors. Cosine similarity is a
	 * measure of similarity between two vectors of an inner product space that
	 * measures the cosine of the angle between them. The cosine of 0° is 1, and it
	 * is less than 1 for any other angle.
	 *
	 * @param queryTfidf
	 *            the first vector that represents a tfIdf vector of the user's
	 *            query
	 * @param docTfidf
	 *            the second vector that represents a tfIdf vector of the document
	 * @return the double
	 */
	private static double cosineSimilarity(double[] queryTfidf, double[] docTfidf) {
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0, length = queryTfidf.length; i < length; i++) {
			dotProduct += queryTfidf[i] * docTfidf[i];
			normA += queryTfidf[i] * queryTfidf[i];
			normB += docTfidf[i] * docTfidf[i];
		}

		return dotProduct * 1.0 / (Math.sqrt(normA) * Math.sqrt(normB));
	}

	/**
	 * Generates tfIdf vector by performing multiplication on elements of the tf and
	 * idf vectors.
	 *
	 * @param idfVector
	 *            the idf vector
	 * @return the double[]
	 */
	private double[] generateTfIdf(double[] idfVector) {
		double[] tfidfVector = new double[tfVector.length];

		for (int i = 0, length = tfidfVector.length; i < length; i++) {
			tfidfVector[i] = tfVector[i] * idfVector[i];
		}

		return tfidfVector;
	}
}
