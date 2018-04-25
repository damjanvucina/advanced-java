package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeCommand extends Command {
	public static final int START_PADDING = 0;
	public static final int PADDING_MARGIN = 2;

	public TreeCommand() {
		super("tree", Arrays.asList("Prints directory tree",
								    "Each directory level shifts output two charatcers to the right"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = arguments.split(" ");
		if(input.length != 1) {
			env.writeln("Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(input[0]);
		if(!Files.exists(path)) {
			env.writeln("Provided file does not exist");
			return ShellStatus.CONTINUE;
		}
		
		printDirectoryTree(path.toFile(), START_PADDING);
		return ShellStatus.CONTINUE;
	}

	private void printDirectoryTree(File directory, int padding) {
		for(File file : directory.listFiles()) {
			printPadding(padding);
			System.out.println(file.getName());
			
			if(file.isDirectory()) {
				printDirectoryTree(directory, padding + PADDING_MARGIN);
			}
			
		}
	}

	private void printPadding(int padding) {
		StringBuilder sb = new StringBuilder(padding);
		
		while(padding-- > 0) {
			sb.append(" ");
		}
		System.out.print(sb.toString());
	}

}
