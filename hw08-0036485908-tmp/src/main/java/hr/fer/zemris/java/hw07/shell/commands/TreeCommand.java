package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The command that prints to the console directory structure of the specified
 * directory.
 * 
 * @author Damjan Vučina
 */
public class TreeCommand extends Command {

	/** The Constant START_PADDING. */
	public static final int START_PADDING = 0;

	/** The Constant PADDING_MARGIN. */
	public static final int PADDING_MARGIN = 2;

	/**
	 * Instantiates a new tree command and provides description that can later be
	 * obtained via help command.
	 */
	public TreeCommand() {
		super("tree", Arrays.asList("Prints directory tree",
				"Each directory level shifts output two charatcers to the right"));
	}

	/**
	 * The method that expects a single argument: directory name and prints a tree
	 * (each directory level shifts output two characters to the right).
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
			return CONTINUE;
		}

		Path path = Paths.get(input[0]);
		if (!Files.exists(path)) {
			env.writeln("Provided file does not exist");
			return CONTINUE;
		}
		File directory = path.toFile();
		if (directory.isFile()) {
			env.writeln("Command " + getCommandName() + " takes a directory as an argument, not a file");
			return CONTINUE;
		}
		if (directory.length() == 0) {
			env.writeln("Provided directory is empty");
			return CONTINUE;
		}

		printDirectoryTree(directory, START_PADDING);
		return CONTINUE;
	}

	/**
	 * Prints the directory tree.
	 *
	 * @param directory
	 *            the directory provided by te user
	 * @param padding
	 *            the current padding used for formatting (each directory level
	 *            shifts output two characters to the right)
	 */
	private void printDirectoryTree(File directory, int padding) {
		for (File file : directory.listFiles()) {
			printPadding(padding);
			System.out.print(file.isFile() ? "FILE: " : "DIR: ");
			System.out.println(file.getName());

			if (file.isDirectory()) {
				printDirectoryTree(file, padding + PADDING_MARGIN);
			}

		}
	}

	/**
	 * Prints the padding.
	 *
	 * @param padding
	 *            the current padding used for formatting (each directory level
	 *            shifts output two characters to the right)
	 */
	private void printPadding(int padding) {
		StringBuilder sb = new StringBuilder(padding);

		while (padding-- > 0) {
			sb.append(" ");
		}
		System.out.print(sb.toString());
	}

}
