package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.MASSRENAME_COMMAND;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.TERMINATE;

public class MassrenameCommand extends Command {
	public static final String MASSRENAME_FILTER = "filter";
	public static final String MASSRENAME_SHOW = "show";
	public static final String MASSRENAME_GROUPS= "groups";
	public static final String MASSRENAME_EXECUTE = "execute";
	public static final int SINGLE_REGEX = 1;
	public static final int DOUBLE_REGEX = 2;
	public static final int MANDATORY_ARGS_NUM = 3;
	
	public MassrenameCommand() {
		super(MASSRENAME_COMMAND, Arrays.asList("Command accepts two directory paths, a string commandID and  regular expression mask.",
										  "In some cases there is a fifth argument as well, as can be read"
										  + " in the class level documentation",
										  "Command is used for renaming/replacing files from the "
										  + "directory represented by te first path",
										  "This command provides user with several subcommands",
										  "For further information look up class level documentation."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		ShellStatus status = identifyMassrenameArguments(env, input);
		
		if(status.equals(TERMINATE)) {
			return CONTINUE;
		}
		
		
		
		
	}

	private ShellStatus identifyMassrenameArguments(Environment env, String[] input) {
		switch (input[2]) {
		
		case MASSRENAME_FILTER:
			return validateMassrenameArguments(env, input, SINGLE_REGEX);
			
		case MASSRENAME_GROUPS:
			return validateMassrenameArguments(env, input, SINGLE_REGEX);
			
		case MASSRENAME_SHOW:
			return validateMassrenameArguments(env, input, DOUBLE_REGEX);
			
		case MASSRENAME_EXECUTE:
			return validateMassrenameArguments(env, input, DOUBLE_REGEX);

		default:
			env.writeln("Invalid massrename subcommand, was: " + input[2]);
			return TERMINATE;
		}
	}
	
	private ShellStatus validateMassrenameArguments(Environment env, String[] input, int numOfRegexes) {
		Path sourceDir = Paths.get(input[0]);
		Path destinationDir = Paths.get(input[1]);
		
		if(Files.exists(sourceDir) && Files.exists(destinationDir)) {
			
			if(Files.isDirectory(sourceDir) && Files.isDirectory(destinationDir)) {
				
				if(input.length == MANDATORY_ARGS_NUM + numOfRegexes) {
					
					while(--numOfRegexes > 0) {
						if(!input[MANDATORY_ARGS_NUM + numOfRegexes].getClass().equals(String.class)) {
							return TERMINATE;
						}
					}
				}
			}
		}
		return CONTINUE;
	}

	
	
	
	
	
	
	
	
	
	
	
}
