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

/**
 * The class that represents a Vocabulary that is used for calucalting the
 * similarity between the documents. This vocabulary consists of words that have
 * been extracted from the directory provided on the application start-up.
 * 
 * @author Damjan Vučina
 */
public class Vocabulary {

	/** The Constant WORD_REGEX. */
	public static final String WORD_REGEX = "\\P{L}+";

	/** The Constant STOP_WORDS_PATH. */
	public static final String STOP_WORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";

	/** The set of known words. */
	private Set<String> words;

	/** The set of stop words. */
	private Set<String> stopWords;

	/** The list. */
	private List<String> list;

	/**
	 * The occurences map that maps words to the number of times they occur in this
	 * document.
	 */
	private Map<String, Integer> occurences;

	/**
	 * Instantiates a new vocabulary and delegates to a helper method for performing
	 * stop words initialization.
	 */
	public Vocabulary() {
		words = new TreeSet<>();
		stopWords = new TreeSet<>();
		occurences = new HashMap<>();

		initializeStopWords();
	}

	/**
	 * Initialize stop words.
	 */
	private void initializeStopWords() {
		Path path = Paths.get(STOP_WORDS_PATH);

		try {
			stopWords.addAll(Files.readAllLines(path, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the words.
	 *
	 * @return the words
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * Gets the stop words.
	 *
	 * @return the stop words
	 */
	public Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * Adds the word to the set.
	 *
	 * @param word
	 *            the word
	 */
	public void add(String word) {
		words.add(word);
	}

	/**
	 * Fills words from file.
	 *
	 * @param file
	 *            the file
	 */
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

	/**
	 * Processes a single line of text file by extracting all its words, removing
	 * stop words and duplicates.
	 *
	 * @param line
	 *            the line
	 * @return the collection
	 */
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

	/**
	 * Gets the size of the list.
	 *
	 * @return the size
	 */
	public int getSize() {
		return list.size();
	}

	/**
	 * Generates the list.
	 */
	public void generateList() {
		list = new ArrayList<>();
		list.addAll(words);
		words.clear();
	}
}
