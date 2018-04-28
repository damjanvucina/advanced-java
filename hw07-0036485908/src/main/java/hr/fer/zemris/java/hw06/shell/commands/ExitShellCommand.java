package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ExitShellCommand.
 */
public class ExitShellCommand extends Command {

	/**
	 * Instantiates a new exit shell command.
	 */
	public ExitShellCommand() {
		super("exit", Arrays.asList("Command used for exiting this shell."));
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}
}
