package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.MyShell.CD_COMMAND;

/**
 * The command that is used for the purpose of changing the working directory
 * of the current java process.
 * 
 * @author Damjan Vučina
 */
public class CdCommand extends Command {

	/**
	 * Instantiates a new cd command and provides description that can later be
	 * obtained via help command.
	 */
	public CdCommand() {
		super(CD_COMMAND, Arrays.asList("Invoked with a single argument",
				"Argument represents the new path to the to be set working directory"));

	}

	/**
	 * Changes the working directory of the current java process.
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

		Path path = getResolved(env, input[0]);

		updateWorkingDirectory(env, path);
		return CONTINUE;
	}

}
