package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.LISTD_COMMAND;
public class ListdCommand extends Command{

	public ListdCommand() {
		super(LISTD_COMMAND, description);
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return null;
	}

}
