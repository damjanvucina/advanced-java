package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.CAT_COMMAND;
/**
 * This class represents a command that opens given file and writes its content
 * to console. Command takes one or two arguments. The first argument is path to
 * some file and is mandatory. The second argument is charset name that should
 * be used to interpret chars from bytes. If not provided, a default platform
 * charset is used.
 * 
 * @author Damjan Vučina
 */
public class CatCommand extends Command {

	/**
	 * Instantiates a new cat command and provides description that can later be
	 * obtained via help command.
	 */
	//@formatter:off
	public CatCommand() {
		super(CAT_COMMAND, Arrays.asList("Opens given file and writes its content to console",
								   "Command takes one or two arguments",
								   "The first argument is path to some file and is mandatory",
								   "The second argument is charset name that should be used to interpret chars from bytes"));

	}
	//@formatter:on

	/**
	 * Method that is charged with execution of the cat command. Command takes one
	 * or two arguments and they are appended within second method argument. The
	 * first argument within is path to some file and is mandatory. The second
	 * argument within is charset name that should be used to interpret chars from
	 * bytes. If not provided, a default platform charset is used.
	 * 
	 * @throws ShellIOException if IOException occured while writing file content.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);

		if (input.length < 1 || input.length > 2) {
			env.writeln("Command " + getCommandName() + " takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}

		Path path =getResolved(env, input[0]);
		if (!Files.exists(path) || Files.isDirectory(path)) {
			env.writeln("First argument must be an existing file.");
			return ShellStatus.CONTINUE;
		}

		Charset charset = (input.length == 2) ? Charset.forName(input[1].trim()) : Charset.defaultCharset();

		try {
			Files.newBufferedReader(path, charset).lines().forEach(line -> env.writeln(line));
		} catch (IOException e) {
			throw new ShellIOException("IOException occured while writing file content.");
		}

		return ShellStatus.CONTINUE;
	}

}
