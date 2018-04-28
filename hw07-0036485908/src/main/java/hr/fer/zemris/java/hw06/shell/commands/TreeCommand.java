package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import static hr.fer.zemris.java.hw06.shell.ShellStatus.CONTINUE;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeCommand.
 */
public class TreeCommand extends Command {
	
	/** The Constant START_PADDING. */
	public static final int START_PADDING = 0;
	
	/** The Constant PADDING_MARGIN. */
	public static final int PADDING_MARGIN = 2;

	/**
	 * Instantiates a new tree command.
	 */
	public TreeCommand() {
		super("tree", Arrays.asList("Prints directory tree",
								    "Each directory level shifts output two charatcers to the right"));
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if(input.length != 1) {
			env.writeln("Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return CONTINUE;
		}
		
		Path path = Paths.get(input[0]);
		if(!Files.exists(path)) {
			env.writeln("Provided file does not exist");
			return CONTINUE;
		}
		File directory = path.toFile();
		if(directory.isFile()) {
			env.writeln("Command " + getCommandName() + " takes a directory as an argument, not a file");
			return CONTINUE;
		}
		if(directory.length() == 0) {
			env.writeln("Provided directory is empty");
			return CONTINUE;
		}
		
		printDirectoryTree(directory, START_PADDING);
		return CONTINUE;
	}

	/**
	 * Prints the directory tree.
	 *
	 * @param directory the directory
	 * @param padding the padding
	 */
	private void printDirectoryTree(File directory, int padding) {
		for(File file : directory.listFiles()) {
			printPadding(padding);
			System.out.print(file.isFile() ? "FILE: " : "DIR: ");
			System.out.println(file.getName());
			
			if(file.isDirectory()) {
				printDirectoryTree(file, padding + PADDING_MARGIN);
			}
			
		}
	}

	/**
	 * Prints the padding.
	 *
	 * @param padding the padding
	 */
	private void printPadding(int padding) {
		StringBuilder sb = new StringBuilder(padding);
		
		while(padding-- > 0) {
			sb.append(" ");
		}
		System.out.print(sb.toString());
	}

}
