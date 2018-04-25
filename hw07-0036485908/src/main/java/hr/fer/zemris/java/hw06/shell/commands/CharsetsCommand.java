package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand extends Command {

	public CharsetsCommand() {
		super("charsets", Arrays.asList("Lists names of supported charsets for user's Java platform."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset.availableCharsets().forEach((charsetName, charsetObject) -> env.writeln(charsetName));
		return ShellStatus.CONTINUE;
	}

}
