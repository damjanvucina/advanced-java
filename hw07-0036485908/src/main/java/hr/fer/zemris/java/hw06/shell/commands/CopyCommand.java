package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyCommand extends Command {
	public static final int BUFFER_SIZE = 4096;

	public CopyCommand() {
		super("copy", Arrays.asList("Copies first file to the second file", 
									"Command expects two arguments", 
									"Source file must be a regular file",
									"Destination file can be a directory"));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = arguments.split(" ");
		if(input.length != 2) {
			env.writeln("Command " + getCommandName() + " takes two arguments, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		
		Path sourceFile = Paths.get(input[0]);
		Path destinationFile = Paths.get(input[1]);
		
		if(Files.isDirectory(sourceFile)) {
			env.writeln("Source file cannot be a directory");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.exists(destinationFile) && destinationFile.toFile().length() != 0) {
			env.writeln("File " + destinationFile.getFileName() + " already exist. Would you like to overwrite it?");
		}
		
		performCopying(sourceFile, destinationFile);
		
		return ShellStatus.CONTINUE;
	}

	private void performCopying(Path sourceFile, Path destinationFile) {
		try(FileInputStream inputStream = new FileInputStream(sourceFile.toFile());
			FileOutputStream outputStream = new FileOutputStream(destinationFile.toFile())){
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int n;
			while((n = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, n);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
