package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.PWD_COMMAND;

/**
 * The command that is used for the purpose of printing the working directory of
 * the current java process to the console.
 * 
 * @author Damjan Vučina
 */
public class PwdCommand extends Command {

	/**
	 * Instantiates a new pwd command and provides description that can later be
	 * obtained via help command.
	 */
	public PwdCommand() {
		super(PWD_COMMAND, Arrays.asList("Invoked without arguments", "Prints working directory path to the console"));

	}

	/**
	 * Prints the working directory of the current java process to the console.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}

		env.writeln("Current working directory is: " + String.valueOf(env.getCurrentDirectory()));
		return CONTINUE;
	}

}
