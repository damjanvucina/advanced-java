package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentSplitterState;
import hr.fer.zemris.java.hw06.shell.ShellCommand;

import static hr.fer.zemris.java.hw06.shell.MyShell.WHITESPACE;
import static hr.fer.zemris.java.hw06.shell.ArgumentSplitterState.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Command.
 */
public abstract class Command implements ShellCommand {
	
	/** The name. */
	private String name;
	
	/** The description. */
	private List<String> description;

	/**
	 * Instantiates a new command.
	 *
	 * @param name the name
	 * @param description the description
	 */
	public Command(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

	/**
	 * Split arguments.
	 *
	 * @param arguments the arguments
	 * @return the string[]
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
