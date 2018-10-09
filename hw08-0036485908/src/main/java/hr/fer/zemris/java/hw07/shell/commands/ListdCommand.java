package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.LISTD_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

/**
 * The command that is used for the purpose of printing all previously used
 * working directories from the shared data map to the console.
 * 
 * @author Damjan Vučina
 */
public class ListdCommand extends Command {

	/**
	 * Instantiates a new listd command and provides description that can later be
	 * obtained via help command.
	 */
	public ListdCommand() {
		super(LISTD_COMMAND, Arrays.asList("Prints paths stored in the internally managed map to the console"));

	}

	/**
	 * Prints all previously used working directories from the shared data map to
	 * the console.
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

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CD_STACK);

		if (stack.isEmpty()) {
			env.writeln("There are no stored working directory paths");

		} else {
			stack.stream().sorted().forEach(path -> env.writeln(String.valueOf(path)));
		}

		return CONTINUE;
	}

}
