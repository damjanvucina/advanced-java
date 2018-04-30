package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.PWD_COMMAND;
public class PwdCommand extends Command{

	public PwdCommand() {
		super(PWD_COMMAND, Arrays.asList("Invoked without arguments", 
								   "Prints working directory path to the console"));
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}
		
		env.writeln("Current working directory is: " + String.valueOf(env.getCurrentDirectory()));
		return CONTINUE;
	}

}
