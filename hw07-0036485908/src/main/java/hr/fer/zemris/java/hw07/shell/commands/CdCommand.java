package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

public class CdCommand extends Command {

	public CdCommand() {
		super("cd", Arrays.asList("Invoked with a single argument",
								  "Argument represents the new path to the to be set working directory"));
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln(
					"Command " + getCommandName() + " takes a single argument, arguments provided: " + input.length);
			return CONTINUE;
		}

		Path path = getResolved(env, input[0]);
		if (!Files.exists(path) || arguments.trim().equals("")) {
			env.writeln("Provided path does not exist");
			return CONTINUE;
		}
		
		if (Files.isRegularFile(path)) {
			env.writeln("Argument must be directory path, not a regular file path.");
			return CONTINUE;
		}
		
		env.setCurrentDirectory(path);
		env.writeln("Current working directory set to: " + String.valueOf(path));
		return CONTINUE;
	}

}
