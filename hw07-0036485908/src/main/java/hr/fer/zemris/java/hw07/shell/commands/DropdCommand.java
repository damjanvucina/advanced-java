package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.DROPD_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

public class DropdCommand extends Command {

	public DropdCommand() {
		super(DROPD_COMMAND, Arrays.asList("Removes last stored working directory path from the internally managed map"));
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}
		
		popFromStack(env, CD_STACK);
		
		return CONTINUE;
	}

}
