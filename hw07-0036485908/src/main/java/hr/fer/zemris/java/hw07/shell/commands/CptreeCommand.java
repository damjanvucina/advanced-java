package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.CPTREE_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

/**
 * The command that is used for the purpose of copying the contents of the
 * provided directory to the other provided directory. If the destination
 * directory exists, source directory with its contents is copied in it. If the
 * destination directory does not exist but its parent directory does, the
 * source directory is copied under the destination directory name. If neither
 * the destination directory nor its parent directory exist, this method has no
 * effect.
 * 
 * @author Damjan Vučina
 */
public class CptreeCommand extends Command {

	/**
	 * Instantiates a new cptree command and provides description that can later be
	 * obtained via help command.
	 */
	public CptreeCommand() {
		super(CPTREE_COMMAND,
				Arrays.asList("Invoked with two path arguments",
						"First path represents a directory to be copied, i.e. source directory",
						"Second path represents the locaton, i.e. destination directory",
						"If the last part of the source directory structure does not exist,"
								+ " shell copies the source directory under that file name",
						"If two or more last parts of the source directory structure do not exist, "
								+ "ShellIOException is thrown"));
	}

	/**
	 * Copies the contents of the provided directory to the other provided
	 * directory. If the destination directory exists, source directory with its
	 * contents is copied in it. If the destination directory does not exist but its
	 * parent directory does, the source directory is copied under the destination
	 * directory name. If neither the destination directory nor its parent directory
	 * exist, this method has no effect.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 2) {
			env.writeln("Command " + getCommandName() + " takes two arguments, arguments provided: " + input.length);
			return CONTINUE;
		}

		Path sourceDir = getResolved(env, input[0]);
		Path destinationDir = getResolved(env, input[1]);

		if (Files.isRegularFile(destinationDir)) {
			env.writeln("Destination file must be a directory, not a regular file.");
			return CONTINUE;

		} else if (Files.isRegularFile(sourceDir)) {
			env.writeln("Source file must be a directory, not a regular file.");
			return CONTINUE;
		}

		if (Files.exists(destinationDir)) {
			performCopying(env, sourceDir, destinationDir.resolve(sourceDir.getFileName()));
			env.writeln("Successfully copied  " + String.valueOf(sourceDir) + " directory.");
		}

		else if (Files.exists(destinationDir.getParent())) {
			Path parent = createDirectory(env, destinationDir);
			performCopying(env, sourceDir, parent);
			env.writeln("Copied  " + String.valueOf(sourceDir) + " directory as " + String.valueOf(parent));
		} else {
			env.writeln("Invalid destination directory.");
		}

		return CONTINUE;
	}

	/**
	 * Helper method which performes copying directory itself. If the destination
	 * directory exists, source directory with its contents is copied in it. If the
	 * destination directory does not exist but its parent directory does, the
	 * source directory is copied under the destination directory name. If neither
	 * the destination directory nor its parent directory exist, this method has no
	 * effect.
	 *
	 * @param env
	 *            the reference to the object that makes communication between user, shell and commands possible
	 * @param sourceDir
	 *            the source directory
	 * @param destinationDir
	 *            the destination directory
	 */
	private void performCopying(Environment env, Path sourceDir, Path destinationDir) {
		try {
			Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
					Files.copy(path, destinationDir.resolve(sourceDir.relativize(path)));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) throws IOException {
					Files.createDirectories(destinationDir.resolve(sourceDir.relativize(path)));
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			env.write("Error deleting directory tree.");
		}
	}

}
