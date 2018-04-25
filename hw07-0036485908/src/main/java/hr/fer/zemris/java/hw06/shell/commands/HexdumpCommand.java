package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpCommand extends Command {

	public HexdumpCommand() {
		super("hexdump", Arrays.asList("Expects a single argument: file name",
									   "Produces hex-output"));
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return null;
	}

}
