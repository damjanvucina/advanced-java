package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("help", Arrays.asList("Command that provides detailed info about the available commands",
									"If started with no arguments, it lists names of all supported commands.",
									"If started with single argument, it prints name and the description of selected command"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);

		if (input.length > 1) {
			env.writeln("Command " + getCommandName() + " takes zero or one arguments, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		if(arguments.length() == 0) {
			env.commands().forEach((name, command) -> env.writeln(name));
			
		} else if (input.length == 1) {
			if(env.commands().containsKey(input[0])) {
				env.commands().get(input[0]).getCommandDescription().forEach(env::writeln);
			}
			env.writeln("Unsupported command provided, was: " + input[0]);
		}
		return ShellStatus.CONTINUE;
	}
	
}
