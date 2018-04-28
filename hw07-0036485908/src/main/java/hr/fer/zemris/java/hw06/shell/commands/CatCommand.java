package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class CatCommand.
 */
public class CatCommand extends Command {

	/**
	 * Instantiates a new cat command.
	 */
	//@formatter:off
	public CatCommand() {
		super("cat", Arrays.asList("Opens given file and writes its content to console",
								   "Command takes one or two arguments",
								   "The first argument is path to some file and is mandatory",
								   "The second argument is charset name that should be used to interpret chars from bytes"));

	}
	//@formatter:on

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#executeCommand(hr.fer.zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		
		if (input.length < 1 || input.length > 2) {
			env.writeln("Command " + getCommandName() + " takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(input[0]);
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
