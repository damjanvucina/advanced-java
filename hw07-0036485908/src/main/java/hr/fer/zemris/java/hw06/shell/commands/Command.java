package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentSplitterState;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import static hr.fer.zemris.java.hw06.shell.MyShell.WHITESPACE;
import static hr.fer.zemris.java.hw06.shell.ArgumentSplitterState.*;

public abstract class Command implements ShellCommand {
	private String name;
	private List<String> description;

	public Command(String name, List<String> description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

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
