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

public class Document {

	private static final double NO_SIMILARITY = 0.0;
	private Path path;
	private double[] tfVector;
	private Vocabulary vocabulary;

	public Document(Path path, Vocabulary vocabulary) {
		this.path = path;
		this.vocabulary = vocabulary;
		tfVector = new double[vocabulary.getSize()];

		initializeTFVector();
	}

	public Document(Vocabulary vocabulary, List<String> queryList) {
		this.vocabulary = vocabulary;
		tfVector = new double[vocabulary.getSize()];

		processInitialization(queryList);
	}

	private void initializeTFVector() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		processInitialization(lines);
	}

	private void processInitialization(List<String> lines) {
		List<String> words = new ArrayList<>();
		for (String line : lines) {
			words.addAll(Arrays.asList(line.split(WORD_REGEX)).stream().map(String::toLowerCase)
					.collect(Collectors.toList()));
		}

		words.removeAll(vocabulary.getStopWords());
		List<String> dictionary = vocabulary.getList();
		for (String word : words) {
			tfVector[dictionary.indexOf(word)]++;
		}
	}

	public Path getPath() {
		return path;
	}

	public double[] getTfVector() {
		return tfVector;
	}

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

	private double[] generateTfIdf(double[] idfVector) {
		double[] tfidfVector = new double[tfVector.length];

		for (int i = 0, length = tfidfVector.length; i < length; i++) {
			tfidfVector[i] = tfVector[i] * idfVector[i];
		}

		return tfidfVector;
	}
}
