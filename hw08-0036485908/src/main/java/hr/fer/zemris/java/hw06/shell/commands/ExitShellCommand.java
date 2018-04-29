package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The class that represents a command that si in charge of closing the MyShell
 * program.
 * 
 * @author Damjan Vučina
 */
public class ExitShellCommand extends Command {

	/**
	 * Instantiates a new exit shell command and provides description that can later
	 * be obtained via help command.
	 */
	public ExitShellCommand() {
		super("exit", Arrays.asList("Command used for exiting this shell."));
	}

	/**
	 * Exits the MyShell program by terminating the processing.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}
}
