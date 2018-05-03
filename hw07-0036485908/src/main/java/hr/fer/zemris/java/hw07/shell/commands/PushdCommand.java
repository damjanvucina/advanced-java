package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;

import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.PUSHD_COMMAND;

/**
 * The command that is used for the purpose of changing the working directory of
 * the current java process to match the directory provided via arguments. This
 * method also stores previous working directory of the java process to the
 * shared data map that can later be obtained and reset vie popd command.
 * 
 * @author Damjan Vučina
 */
public class PushdCommand extends Command {

	/**
	 * Instantiates a new pushd command and provides description that can later be
	 * obtained via help command.
	 */
	public PushdCommand() {
		super(PUSHD_COMMAND, Arrays.asList("Pushes current working directory to stack",
				"Stack is stored in internally managed map", "Sets provided directory path as working directory"));

	}

	/**
	 * Changes the working directory of the current java process and stores previous
	 * working directory of the java process to the shared data map that can later
	 * be obtained and reset vie popd command.
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
		if (!Files.exists(path)) {
			env.writeln("Provided path does not exist");
			return CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CD_STACK);

		stack.push(clonePath(env.getCurrentDirectory()));
		env.setSharedData(CD_STACK, stack);

		updateWorkingDirectory(env, path);
		return CONTINUE;
	}

}
