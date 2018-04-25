package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import static hr.fer.zemris.java.hw06.shell.MyShell.WHITESPACE;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class LsCommand extends Command {

	public LsCommand() {
		super("ls", Arrays.asList("Command takes a single argument(directory) and writes a directory listing.",
				"Does not use recursion."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln(
					"Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(input[0]);
		if (!Files.exists(path)) {
			env.writeln("Provided file does not exist");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
					getFileRepresentation(path, attributes);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) {
					getFileRepresentation(path, attributes);

					return FileVisitResult.CONTINUE;
				}

				private void getFileRepresentation(Path file, BasicFileAttributes attributes) {
					StringBuilder sb = new StringBuilder();

					sb.append(Files.isDirectory(file) ? "d" : "-");
					sb.append(Files.isReadable(file) ? "r" : "-");
					sb.append(Files.isWritable(file) ? "w" : "-");
					sb.append(Files.isExecutable(file) ? "x " : "-");

					sb.append(String.format("%10s", attributes.size()));
					sb.append(WHITESPACE);
					sb.append(String.format("%10s", acquireCreationTime(attributes)));
					sb.append(WHITESPACE);
					sb.append(file.getFileName());

					env.writeln(sb.toString());
				}

				private String acquireCreationTime(BasicFileAttributes attributes) {
					FileTime fileTime = attributes.creationTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					return formattedDateTime;
				}
			});
		} catch (IOException e) {
			env.write("Error inspecting directory tree.");
		}

		return ShellStatus.CONTINUE;

	}

}
