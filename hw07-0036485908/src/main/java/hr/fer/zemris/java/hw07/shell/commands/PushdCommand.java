package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;

import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

public class PushdCommand extends Command {

	public PushdCommand() {
		super("pushd", Arrays.asList("Pushes current working directory to stack",
									 "Stack is stored in internally managed map",
									 "Sets provided directory path as working directory"));

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 1) {
			env.writeln("Command " + getCommandName() + 
						" takes a single argument, arguments provided: " + input.length);
			
			return CONTINUE;
		}

		Path path = getResolved(env, input[0]);
		if (!Files.exists(path)) {
			env.writeln("Provided file does not exist");
			return CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CD_STACK);
		
		stack.push(clonePath(env.getCurrentDirectory()));
		env.setSharedData(CD_STACK, stack);
		
		updateWorkingDirectory(env, path, arguments);
		return CONTINUE;
	}

}
