package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.HELP_COMMAND;
/**
 * The command that is used for checking out the supported commands or for
 * obtaining more detailed information about a specified command.
 * 
 * @author Damjan Vučina
 */
public class HelpCommand extends Command {

	/**
	 * Instantiates a new help command and provides description that can later be
	 * obtained via help command.
	 */
	public HelpCommand() {
		super(HELP_COMMAND,
				Arrays.asList("Command that provides detailed info about the available commands",
						"If started with no arguments, it lists names of all supported commands.",
						"If started with single argument, it prints name and the description of selected command"));
	}

	/**
	 * Method that is in charge of the execution of the help command. If the command
	 * is started with no arguments, it lists names of all supported commands. If
	 * started with single argument, it prints name and the description of selected
	 * command (or print appropriate error message if no such command exists).
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);

		if (input.length > 1) {
			env.writeln("Command " + getCommandName() + " takes zero or one arguments, arguments provided: "
					+ input.length);
			return ShellStatus.CONTINUE;
		}
		if (arguments.length() == 0) {
			env.commands().forEach((name, command) -> env.writeln(name));

		} else if (input.length == 1) {
			if (env.commands().containsKey(input[0])) {
				env.writeln("Description for the command: " + input[0].toUpperCase());
				env.commands().get(input[0]).getCommandDescription().forEach(env::writeln);
			} else {
				env.writeln("Unsupported command provided, was: " + input[0]);
			}

		}
		return ShellStatus.CONTINUE;
	}

}
