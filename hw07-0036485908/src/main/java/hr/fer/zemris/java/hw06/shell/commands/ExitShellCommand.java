package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class ExitShellCommand extends Command {

	public ExitShellCommand() {
		super("exit", Arrays.asList("Command used for exiting this shell."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}
}
