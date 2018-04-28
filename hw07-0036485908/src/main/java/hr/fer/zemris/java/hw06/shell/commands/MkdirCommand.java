package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class MkdirCommand.
 */
public class MkdirCommand extends Command {

	/**
	 * Instantiates a new mkdir command.
	 */
	public MkdirCommand() {
		super("mkdir", Arrays.asList("Command takes a single argument: directory name", 
									 "Creates the appropriate directory structure"));
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if(input.length != 1) {
			env.writeln("Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(input[0]);
		
		if(Files.exists(path)) {
			env.writeln("File with that name already exists.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectory(path);
			env.writeln("File " + path + " created successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ShellStatus.CONTINUE;
	}

}
