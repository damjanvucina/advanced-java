package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Vocabulary {
	public static final String WORD_REGEX = "\\P{L}+";

	private Set<String> words;

	public Vocabulary() {
		words = new HashSet<>();
	}

	public Set<String> getWords() {
		return words;
	}

	public void add(String word) {
		words.add(word);
	}

	public void fillFromFile(Path file) {
		Objects.requireNonNull(file, "File path cannot be null.");

		List<String> lines = null;
		try {
			lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : lines) {
			words.addAll(processLine(line));
		}
	}

	//@formatter:off
	private Collection<String> processLine(String line) {
		return Arrays.asList(line.split(WORD_REGEX))
								 .stream()
								 .map(String::toLowerCase)
								 .collect(Collectors.toSet());
	}
	//@formatter:on
}
