package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ArgumentSplitterState.*;
import static hr.fer.zemris.java.hw07.shell.MyShell.WHITESPACE;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.ArgumentSplitterState;
import hr.fer.zemris.java.hw07.shell.ShellCommand;

/**
 * The abstract base class that represents a single command. Every command class
 * of this MyShell must extended this class at the same time implementing the
 * environment interface used for uniforming the communication between the user,
 * shell and a specific command.
 * 
 * @author Damjan Vučina
 */
public abstract class Command implements ShellCommand {

	/** The name of the command. */
	private String name;

	/**
	 * The description of the command that can later be obtained via help method.
	 */
	private List<String> description;

	/**
	 * Instantiates a new command with the specified name and description.
	 *
	 * @param name
	 *            the name of the command
	 * @param description
	 *            the description of the command
	 */
	public Command(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the name of the specified command
	 * 
	 * @return name of the specified command
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Gets the description of the specified command
	 * 
	 * @return description of the specified command
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

	/**
	 * Splits the arguments of the command that have already been extracted from the
	 * user's query by the MyShell class. Multiple arguments are all appended
	 * together to a single string splitted by the whitespaces.
	 *
	 * @param arguments
	 *            the arguments of the command to be split apart
	 * @return the string array consisting of the command's arguments
	 */
	public String[] splitArguments(String arguments) {
		if (!arguments.contains("\"")) {
			return arguments.split(WHITESPACE);

		} else {
			List<String> result = new LinkedList<>();
			StringBuilder sb = new StringBuilder();
			ArgumentSplitterState splitterState = NONPATH;

			for (char c : arguments.toCharArray()) {

				if (splitterState == NONPATH) {
					if (c == '\"') {
						splitterState = PATH;

					} else if (c == ' ') {
						if (sb.length() != 0) {
							result.add(sb.toString());
							sb.setLength(0);
						} else {
							continue;
						}

					} else {
						sb.append(c);
					}

				} else if (splitterState == PATH) {
					if (c == '\"') {
						result.add(sb.toString());
						splitterState = NONPATH;
						sb.setLength(0);

					} else {
						sb.append(c);
					}
				}
			}

			return result.stream().toArray(String[]::new);
		}
	}
}
