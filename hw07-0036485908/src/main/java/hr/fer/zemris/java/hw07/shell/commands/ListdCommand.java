package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.LISTD_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

public class ListdCommand extends Command{

	public ListdCommand() {
		super(LISTD_COMMAND, Arrays.asList("Prints paths stored in the internally managed map to the console"));
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CD_STACK);
		
		if(stack.isEmpty()) {
			env.writeln("There are no stored working directory paths");
		} else {
			stack.stream()
				 .sorted()
				 .forEach(path -> env.writeln(String.valueOf(path)));
		}
		
		return CONTINUE;
	}

}
