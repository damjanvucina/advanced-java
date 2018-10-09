package hr.fer.zemris.java.hw07.shell.commands;

import static hr.fer.zemris.java.hw07.shell.ArgumentSplitterState.*;
import static hr.fer.zemris.java.hw07.shell.MyShell.WHITESPACE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.ArgumentSplitterState;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.UNICODE_CASE;

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
	 * Gets the name of the specified command.
	 *
	 * @return name of the specified command
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Gets the description of the specified command.
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

	/**
	 * Resolves other file to this path.
	 *
	 * @param env
	 *            the reference to the object that makes communicaton between user,
	 *            shell and commands possible
	 * @param other
	 *            the other file
	 * @return the resolved file path
	 */
	public Path getResolved(Environment env, String other) {
		return env.getCurrentDirectory().resolve(other).normalize();
	}

	/**
	 * Clones path.
	 *
	 * @param path
	 *            the path
	 * @return the path
	 */
	public Path clonePath(Path path) {
		return Paths.get(String.valueOf(path));
	}

	/**
	 * Updates the working directory.
	 *
	 * @param env
	 *            the reference to the object that makes communicaton between user,
	 *            shell and commands possible
	 * @param path
	 *            the path
	 */
	public void updateWorkingDirectory(Environment env, Path path) {
		if (!Files.exists(path)) {
			env.writeln("Provided path does not exist");
		}

		else if (Files.isRegularFile(path)) {
			env.writeln("Argument must be directory path, not a regular file path.");

		} else {
			env.setCurrentDirectory(path);
			env.writeln("Current working directory set to: " + String.valueOf(path));
		}
	}

	/**
	 * Pops from shared data's stack.
	 *
	 * @param env
	 *            the reference to the object that makes communicaton between user,
	 *            shell and commands possible
	 * @param stackId
	 *            the stack id
	 * @return the path
	 */
	public Path popFromStack(Environment env, String stackId) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(stackId);

		if (stack.isEmpty()) {
			env.writeln("Internally managed stack is empty");
			return null;
		}

		Path poppedPath = stack.pop();
		env.setSharedData(stackId, stack);

		return poppedPath;
	}

	/**
	 * Creates a directory.
	 *
	 * @param env
	 *            the reference to the object that makes communicaton between user,
	 *            shell and commands possible
	 * @param path
	 *            the path
	 * @return the path
	 */
	public Path createDirectory(Environment env, Path path) {
		Path p = null;

		try {
			p = Files.createDirectory(path);
			env.writeln("File " + path + " created successfully");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Lists the directory, matches its files against the received regular
	 * expression and invokes consumer method depending on the outcome of the
	 * matching operation.
	 *
	 * @param fileName
	 *            the file name
	 * @param regex
	 *            the regex
	 * @param action
	 *            the action
	 */
	public void directoryWalker(String fileName, String regex, BiConsumer<Path, Matcher> action) {
		Path sourceDir = Paths.get(fileName);
		Pattern pattern = Pattern.compile(regex, CASE_INSENSITIVE & UNICODE_CASE);

		for (File file : sourceDir.toFile().listFiles()) {
			Matcher matcher = pattern.matcher(file.toPath().getFileName().toString());
			if ((matcher.matches())) {
				action.accept(file.toPath(), matcher);
			}
		}
	}

}
