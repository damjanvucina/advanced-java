package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.POPD_COMMAND;

/**
 * The command that is used for the purpose of changing the working directory of
 * the current java process by popping a working directory path from the shared
 * data map and setting it as working directory.
 * 
 * @author Damjan Vučina
 */
public class PopdCommand extends Command {

	/**
	 * Instantiates a new popd command and provides description that can later be
	 * obtained via help command.
	 */
	public PopdCommand() {
		super(POPD_COMMAND, Arrays.asList("Pops value from internally managed stack of paths",
				"Sets that path as working directory path"));

	}

	/**
	 * Changes the working directory of the current java process by popping a
	 * working directory path from the shared data map and setting it as working
	 * directory.
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

		Path poppedPath = popFromStack(env, CD_STACK);

		if (poppedPath != null) {
			updateWorkingDirectory(env, poppedPath);
		}
		return CONTINUE;
	}

}
