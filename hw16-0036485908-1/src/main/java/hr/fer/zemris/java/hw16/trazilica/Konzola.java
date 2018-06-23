package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Konzola {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException(
					"You must provide a single path argument; arguments provided: " + args.length);
		}

		Path folder = Paths.get(args[0]);

		if (Files.notExists(folder) || !Files.isReadable(folder)) {
			throw new IllegalArgumentException("Cannot access folder.");
		}

		Vocabulary vocabulary = new Vocabulary();

		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				vocabulary.fillFromFile(file);
				
				return CONTINUE;
			}

		});

		for(String word : vocabulary.getWords()) {
			System.out.println(word);
		}
	}
}
