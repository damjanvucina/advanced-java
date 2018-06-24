package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static hr.fer.zemris.java.hw16.trazilica.Vocabulary.WORD_REGEX;

public class Document {

	private Path path;
	private double[] tfVector;
	private Vocabulary vocabulary;

	public Document(Path path, Vocabulary vocabulary) {
		this.path = path;
		this.vocabulary = vocabulary;
		tfVector = new double[vocabulary.getSize()];

		initializeTFVector();
	}

	private void initializeTFVector() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
}
