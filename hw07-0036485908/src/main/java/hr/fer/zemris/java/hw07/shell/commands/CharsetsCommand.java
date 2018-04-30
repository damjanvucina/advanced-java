package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

import java.nio.charset.Charset;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names of supported charsets for
 * users Java platform. A single charset name is written per line.
 * 
 * @author Damjan Vučina
 */
public class CharsetsCommand extends Command {

	/**
	 * Instantiates a new charsets command and provides description that can later
	 * be obtained via help command..
	 */
	public CharsetsCommand() {
		super("charsets", Arrays.asList("Lists names of supported charsets for user's Java platform."));
	}

	/**
	 * Method that is in charge of the execution of the charsets commands. Command
	 * charsets takes no arguments and lists names of supported charsets for users
	 * Java platform. A single charset name is written per line.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of
	 * the specified command. MyShell program will end by terminating only if there
	 * is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}

		Charset.availableCharsets().forEach((charsetName, charsetObject) -> env.writeln(charsetName));
		return CONTINUE;
	}
}
