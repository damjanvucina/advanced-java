package hr.fer.zemris.java.hw07.shell.commands;

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
		updateWorkingDirectory(env, path, arguments);
		
		return CONTINUE;
	}

}
