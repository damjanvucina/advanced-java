package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Vocabulary {
	public static final String WORD_REGEX = "\\P{L}+";
	public static final String STOP_WORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";

	private Set<String> words;
	private Set<String> stopWords;
	private List<String> list;
	private Map<String, Integer> occurences;

	public Vocabulary() {
		words = new TreeSet<>();
		stopWords = new TreeSet<>();
		occurences = new HashMap<>();

		initializeStopWords();
	}

	private void initializeStopWords() {
		Path path = Paths.get(STOP_WORDS_PATH);

		try {
			stopWords.addAll(Files.readAllLines(path, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<String> getWords() {
		return words;
	}

	public Set<String> getStopWords() {
		return stopWords;
	}

	public List<String> getList() {
		return list;
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
		List<String> temp = Arrays.asList(line.split(WORD_REGEX))
								  .stream()
								  .map(String::toLowerCase)
								  .collect(Collectors.toList());
		
		temp.stream().forEach(word -> occurences.compute(word, (key, value) -> value == null ? 1 : value + 1));
		
		Set<String> set = new HashSet<>(temp);
		set.removeAll(stopWords);
		return set;
	}
	//@formatter:on

	public int getSize() {
		return list.size();
	}

	public void generateList() {
		list = new ArrayList<>();
		list.addAll(words);
		words.clear();
	}
}
