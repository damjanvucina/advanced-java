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

/**
 * The command that is used for the process of copying a file from one location
 * to the another. Source file can only be a regular file but the destination
 * file can be a directory as well. If the destionation file is in fact a
 * directory the source file is copied in it.
 * 
 * @author Damjan Vučina
 */
public class CopyCommand extends Command {

	/** The constant that defines the size of the buffer used for copying. */
	public static final int BUFFER_SIZE = 4096;

	/** The constant that signifies overwriting of another file */
	public static final String DO_OVERWRITE = "yes";

	/** The constant that signifies the abortion of overwriting of another file */
	public static final String DONT_OVERWRITE = "no";

	/**
	 * Instantiates a new copy command and provides description that can later be
	 * obtained via help command.
	 */
	public CopyCommand() {
		super("copy", Arrays.asList("Copies first file to the second file", "Command expects two arguments",
				"Source file must be a regular file", "Destination file can be a directory"));
	}

	/**
	 * Mehtod that is charged with the process of copying the source file to the
	 * destination location. If the destination file already exists, user is asked
	 * if overwriting of the old file is to be performed. Source file can only be a
	 * regular file but the destination file can be a directory as well. If the
	 * destionation file is in fact a directory the source file is copied in it.
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length != 2) {
			env.writeln("Command " + getCommandName() + " takes two arguments, arguments provided: " + input.length);
			return ShellStatus.CONTINUE;
		}
		Path sourceFile = Paths.get(input[0]);
		Path destinationFile = Paths.get(input[1]);

		if (!Files.exists(sourceFile)) {
			env.writeln("File " + sourceFile + " does not exist.");
			return CONTINUE;
		}
		destinationFile = checkIfDirectory(sourceFile, destinationFile);

		if (Files.isDirectory(sourceFile)) {
			env.writeln("Source file cannot be a directory");
			return CONTINUE;
		}

		if (Files.isDirectory(destinationFile) && Files.notExists(destinationFile)) {
			env.writeln("Destination directory does not exist");
			return CONTINUE;
		}

		if (!destinationFile.toString().contains(".") && Files.notExists(destinationFile)) {
			env.writeln("Destination directory " + destinationFile + " does not exist");
			return CONTINUE;
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

	/**
	 * Checks if the specified destination file is a directory or a normal file.
	 *
	 * @param sourceFile
	 *            the source file
	 * @param destinationFile
	 *            the destination file
	 * @return the path of the destination file
	 */
	private Path checkIfDirectory(Path sourceFile, Path destinationFile) {
		if (destinationFile.toFile().isDirectory()) {
			return destinationFile.resolve(sourceFile.getFileName());
		}

		return destinationFile;
	}

	/**
	 * Check if destination file exists.
	 *
	 * @param env
	 *            the reference to the object in charge of making the communication
	 *            between the user, shell and specific command possible
	 * @param destinationFile
	 *            the destination file
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
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

	/**
	 * Prints the overwriting message.
	 *
	 * @param env
	 *            the reference to the object in charge of making the communication
	 *            between the user, shell and specific command possible
	 */
	private void printOverwritingMessage(Environment env) {
		env.write("If you would like to overwrite it, enter \"" + DO_OVERWRITE + "\", otherwise enter \""
				+ DONT_OVERWRITE + "\": ");
	}

	/**
	 * Performs the copying of the source file to the destination location.
	 *
	 * @param sourceFile
	 *            the source file
	 * @param destinationFile
	 *            the destination file
	 */
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
