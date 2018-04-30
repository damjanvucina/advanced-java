package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.Dispatcher.CD_STACK;

import java.nio.file.Path;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.POPD_COMMAND;

public class PopdCommand extends Command {

	public PopdCommand() {
		super(POPD_COMMAND, Arrays.asList("Pops value from internally managed stack of paths",
				"Sets that path as working directory path"));

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!(arguments.trim().equals(""))) {
			env.writeln("Command " + getCommandName() + " takes zero arguments");
			return CONTINUE;
		}

		Path poppedPath = popFromStack(env, CD_STACK);
		
		if(poppedPath != null) {
			updateWorkingDirectory(env, poppedPath);
		}
		return CONTINUE;
	}

}
