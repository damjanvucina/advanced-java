package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.MKDIR_COMMAND;
/**
 * The command that takes a single argument: directory name, and creates the
 * appropriate directory structure.
 * 
 * @author Damjan Vučina
 */
public class MkdirCommand extends Command {

	/**
	 * Instantiates a new mkdir command and provides description that can later be
	 * obtained via help command.
	 */
	public MkdirCommand() {
		super(MKDIR_COMMAND, Arrays.asList("Command takes a single argument: directory name",
				"Creates the appropriate directory structure"));
	}

	/**
	 * Creates a directory structure for the specified path provided via argument.
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
			return ShellStatus.CONTINUE;
		}

		Path path = getResolved(env, input[0]);

		if (Files.exists(path)) {
			env.writeln("File with that name already exists.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectory(path);
			env.writeln("File " + path + " created successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ShellStatus.CONTINUE;
	}

}
