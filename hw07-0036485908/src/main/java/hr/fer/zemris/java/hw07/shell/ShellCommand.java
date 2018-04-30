package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * The interface that all supported commands must implement to provide an
 * uniform system of communication between the user, the command and the shell.
 * This interface provides methods for executing a specific command and for
 * getting the command name and its description.
 * 
 * @author Damjan Vučina
 */
public interface ShellCommand {

	/**
	 * Executes the specified command.
	 *
	 * @param env
	 *            the env
	 * @param arguments
	 *            the arguments
	 * @return the shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Gets the command name.
	 *
	 * @return the command name
	 */
	String getCommandName();

	/**
	 * Gets the command description.
	 *
	 * @return the command description
	 */
	List<String> getCommandDescription();
}
