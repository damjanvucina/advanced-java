package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.regex.Pattern;

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
		ShellStatus status = processMassrenameCommands(env, input);
		
		if(status.equals(TERMINATE)) {
			return CONTINUE;
		}
		
		return CONTINUE;
	}
	
	//massrename C:\Users\D4MJ4N\Desktop\a C:\Users\D4MJ4N\Desktop\b filter "slika\d+-[^.]+\.jpg"

	public ShellStatus processMassrenameCommands(Environment env, String[] input) {
		ShellStatus status = CONTINUE;
		switch (input[2].trim()) {
		
		case MASSRENAME_FILTER:
			status = validateMassrenameArguments(env, input, SINGLE_REGEX);		
			if(status == TERMINATE) {
				return CONTINUE;
			}
			
			processFilterSubcommand(env, input[0], input[3]);
			break;
			
		case MASSRENAME_GROUPS:
//			status = validateMassrenameArguments(env, input, SINGLE_REGEX);
			
		case MASSRENAME_SHOW:
//			status = validateMassrenameArguments(env, input, DOUBLE_REGEX);
			
		case MASSRENAME_EXECUTE:
//			status = validateMassrenameArguments(env, input, DOUBLE_REGEX);

		default:
			env.writeln("Invalid massrename subcommand, was: " + input[2]);
			return TERMINATE;
		}
		
		return CONTINUE;
	}
	
	private void processFilterSubcommand(Environment env, String fileName, String regex) {
		Path sourceDir = Paths.get(fileName);
		Pattern pattern = Pattern.compile(regex);
		
		try {
			Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
					if(pattern.matcher(path.getFileName().toString()).matches()) {
						env.writeln(path.toString());
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			env.write("Error deleting directory tree.");
		}
	}

	public ShellStatus validateMassrenameArguments(Environment env, String[] input, int numOfRegexes) {
		Path sourceDir = Paths.get(input[0]);
		
		if(Files.exists(sourceDir)) {
			
			if(Files.isDirectory(sourceDir)) {
				
				if(input.length == MANDATORY_ARGS_NUM + numOfRegexes) {
					
					while(numOfRegexes-- > 0) {
						if(!input[MANDATORY_ARGS_NUM + numOfRegexes].getClass().equals(String.class)) {
							env.writeln("Invalid arguments provided.");
							return TERMINATE;
						}
					}
					
					return CONTINUE;
				}
			}
		}
		env.writeln("Invalid arguments provided.");
		return TERMINATE;
	}

}
