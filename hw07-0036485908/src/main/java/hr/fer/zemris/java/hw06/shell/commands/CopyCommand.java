package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellStatus.*;

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
	public static final String DO_OVERWRITE = "yes";
	public static final String DONT_OVERWRITE = "no";

	public CopyCommand() {
		super("copy", Arrays.asList("Copies first file to the second file", "Command expects two arguments",
				"Source file must be a regular file", "Destination file can be a directory"));
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 2) {
			env.writeln("Command " + getCommandName() + " takes two arguments, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		
		Path sourceFile = Paths.get(input[0]);
		Path destinationFile = Paths.get(input[1]);

		destinationFile = checkIfDirectory(sourceFile, destinationFile);

		if (Files.isDirectory(sourceFile)) {
			env.writeln("Source file cannot be a directory");
			return ShellStatus.CONTINUE;
		}

		ShellStatus status = checkIfDestinationFileExists(env, destinationFile);
		if (status == TERMINATE) {
			env.writeln("Copying file " + sourceFile.getFileName() + " aborted");
			return CONTINUE;
		}

		performCopying(sourceFile, destinationFile);
		env.writeln("File " + sourceFile.getFileName() + " has been successfully copied to "
				+ destinationFile.getFileName());

		return ShellStatus.CONTINUE;
	}

	private Path checkIfDirectory(Path sourceFile, Path destinationFile) {
		if (destinationFile.toFile().isDirectory()) {
			return destinationFile.resolve(sourceFile.getFileName());
		}

		return destinationFile;
	}

	private ShellStatus checkIfDestinationFileExists(Environment env, Path destinationFile) {
		if (Files.exists(destinationFile) && destinationFile.toFile().length() != 0) {
			env.writeln("File " + destinationFile.getFileName() + " already exist.");
			printOverwritingMessage(env);

			String response = env.readLine();
			while (!response.equalsIgnoreCase(DO_OVERWRITE) && !response.equalsIgnoreCase(DONT_OVERWRITE)) {

				env.writeln("Invalid input.");
				printOverwritingMessage(env);
				response = env.readLine();
			}

			if (response.equals(DONT_OVERWRITE)) {
				return TERMINATE;

			} else {
				return CONTINUE;
			}
		}

		return CONTINUE;
	}
	

	private void printOverwritingMessage(Environment env) {
		env.write("If you would like to overwrite it, enter \"" + DO_OVERWRITE + "\", otherwise enter \""
				+ DONT_OVERWRITE + "\": ");
	}

	//@formatter:off
	private void performCopying(Path sourceFile, Path destinationFile) {
		
		try (FileInputStream inputStream = new FileInputStream(sourceFile.toFile());
			 FileOutputStream outputStream = new FileOutputStream(destinationFile.toFile())) {

			byte[] buffer = new byte[BUFFER_SIZE];
			int n;
			while ((n = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, n);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//@formatter:on

}
