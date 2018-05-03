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
import static hr.fer.zemris.java.hw07.shell.MyShell.RMTREE_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

/**
 * The command that is used for the purpose of deleting the contents of the
 * provided directory.
 * 
 * @author Damjan Vučina
 */
public class RmtreeCommand extends Command {

	/**
	 * Instantiates a new rmtree command and provides description that can later be
	 * obtained via help command.
	 */
	public RmtreeCommand() {
		super(RMTREE_COMMAND, Arrays.asList("Argument must be an existing directory",
				"Removes this directory and its entire subtree"));

	}

	/**
	 * Deletes the contents of the provided directory.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln(
					"Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}

		Path path = getResolved(env, input[0]);

		if (!Files.exists(path)) {
			env.writeln("Provided path does not exist");
		}

		else if (Files.isRegularFile(path)) {
			env.writeln("Argument must be directory path, not a regular file path.");

		} else {
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
						env.writeln("Deleting file: " + path);
						Files.delete(path);

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
						env.writeln("Deleting directory: " + path);
						Files.delete(path);

						return FileVisitResult.CONTINUE;
					}

				});
			} catch (IOException e) {
				env.write("Error deleting directory tree.");
			}

		}
		return CONTINUE;
	}

}
