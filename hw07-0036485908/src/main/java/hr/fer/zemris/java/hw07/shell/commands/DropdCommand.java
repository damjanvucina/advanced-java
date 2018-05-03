package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.DROPD_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

/**
 * The command that is used for the purpose of removing a previously used
 * working directory from the shared data map. The current working directory is
 * not affected.
 * 
 * @author Damjan Vučina
 */
public class DropdCommand extends Command {

	/**
	 * Instantiates a new dropd command and provides description that can later be
	 * obtained via help command.
	 */
	public DropdCommand() {
		super(DROPD_COMMAND,
				Arrays.asList("Removes last stored working directory path from the internally managed map"));

	}

	/**
	 * Removes a previously used working directory from the shared data map. The
	 * current working directory is not affected.
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

		popFromStack(env, CD_STACK);

		return CONTINUE;
	}

}
