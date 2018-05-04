package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import static hr.fer.zemris.java.hw07.shell.MyShell.MASSRENAME_COMMAND;
import static hr.fer.zemris.java.hw07.shell.MyShell.WHITESPACE;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;
import static hr.fer.zemris.java.hw07.shell.ShellStatus.TERMINATE;

/**
 * The command that is used for the purpose of renaming or moving multiple files
 * from the directory. The command representaton is massrename DIR1 DIR2
 * subcommand MASK OTHER. DIR1 is source directory. DIR2 is destination
 * directory. This command supports four subcommands: filter, groups, show and
 * execute. MASK is regular expression used for filtering files liable for
 * processing. OTHER is regular expression used for reproducing a new name for
 * files to be renamed/moved. Filter subcommand prints files which match the
 * given regular rexpression to the console. Groups subcommand prints files
 * which match the given regular rexpression to the console as well as each of
 * the regular expression group mappings. Show subcommand prints files which
 * match the given regular rexpression to the console with their names
 * transformed depending on the OTHER attribute. Execute subcommand does the
 * same as show subcommand but performs the remaning/moving files as well.
 * 
 * @author Damjan Vučina
 */
public class MassrenameCommand extends Command {

	/** The Constant MASSRENAME_FILTER. */
	public static final String MASSRENAME_FILTER = "filter";

	/** The Constant MASSRENAME_SHOW. */
	public static final String MASSRENAME_SHOW = "show";

	/** The Constant MASSRENAME_GROUPS. */
	public static final String MASSRENAME_GROUPS = "groups";

	/** The Constant MASSRENAME_EXECUTE. */
	public static final String MASSRENAME_EXECUTE = "execute";

	/** The Constant SINGLE_REGEX. */
	public static final int SINGLE_REGEX = 1;

	/** The Constant DOUBLE_REGEX. */
	public static final int DOUBLE_REGEX = 2;

	/** The Constant MANDATORY_ARGS_NUM. */
	public static final int MANDATORY_ARGS_NUM = 3;

	/** The Constant SHOW_SEPARATOR. */
	public static final String SHOW_SEPARATOR = " => ";

	/**
	 * Instantiates a new massrename command and provides description that can later
	 * be obtained via help command.
	 */
	public MassrenameCommand() {
		super(MASSRENAME_COMMAND, Arrays.asList(
				"Command accepts two directory paths, a string commandID and  regular expression mask.",
				"In some cases there is a fifth argument as well, as can be read" + " in the class level documentation",
				"Command is used for renaming/replacing files from the " + "directory represented by te first path",
				"This command provides user with several subcommands",
				"For further information look up class level documentation."));
	}

	/**
	 * Renames or moving multiple files from the directory. The command
	 * representaton is massrename DIR1 DIR2 subcommand MASK OTHER. DIR1 is source
	 * directory. DIR2 is destination directory. This command supports four
	 * subcommands: filter, groups, show and execute. MASK is regular expression
	 * used for filtering files liable for processing. OTHER is regular expression
	 * used for reproducing a new name for files to be renamed/moved. Filter
	 * subcommand prints files which match the given regular rexpression to the
	 * console. Groups subcommand prints files which match the given regular
	 * rexpression to the console as well as each of the regular expression group
	 * mappings. Show subcommand prints files which match the given regular
	 * rexpression to the console with their names transformed depending on the
	 * OTHER attribute. Execute subcommand does the same as show subcommand but
	 * performs the remaning/moving files as well..
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		ShellStatus status = processMassrenameCommands(env, input);

		if (status.equals(TERMINATE)) {
			return CONTINUE;
		}

		return CONTINUE;
	}

	/**
	 * Processes massrename command by identifiying subcommand and invoking methods
	 * which perform further processing.
	 *
	 * @param env
	 *            the reference to the object that makes communication between user,
	 *            shell and commands possible
	 * @param input
	 *            the input
	 * @return the shell status
	 */
	public ShellStatus processMassrenameCommands(Environment env, String[] input) {
		ShellStatus status = CONTINUE;
		NameBuilderParser parser;
		NameBuilder builder;

		switch (input[2].trim()) {

		case MASSRENAME_FILTER:
			status = validateMassrenameArguments(env, input, SINGLE_REGEX);
			if (status == TERMINATE) {
				return CONTINUE;
			}

			directoryWalker(input[0], input[3], (path, matcher) -> env.writeln(path.getFileName().toString()));
			break;

		case MASSRENAME_GROUPS:
			status = validateMassrenameArguments(env, input, SINGLE_REGEX);
			if (status == TERMINATE) {
				return CONTINUE;
			}

			directoryWalker(input[0], input[3], new BiConsumer<Path, Matcher>() {
				@Override
				public void accept(Path path, Matcher matcher) {
					env.write(path.getFileName().toString() + WHITESPACE);

					for (int i = 0, count = matcher.groupCount(); i <= count; i++) {
						env.write(String.valueOf(i) + ": ");
						env.write(matcher.group(i) + WHITESPACE);
					}

					env.writeln("");
				}
			});
			break;

		case MASSRENAME_SHOW:
			status = validateMassrenameArguments(env, input, DOUBLE_REGEX);
			if (status == TERMINATE) {
				return CONTINUE;
			}

			parser = new NameBuilderParser(input[4]);
			builder = parser.getNameBuilder();

			invokeGroupingWalker(input, builder, path -> System.out.print(path.toFile().getName() + SHOW_SEPARATOR));

			break;

		case MASSRENAME_EXECUTE:
			status = validateMassrenameArguments(env, input, DOUBLE_REGEX);
			if (status == TERMINATE) {
				return CONTINUE;
			}

			parser = new NameBuilderParser(input[4]);
			builder = parser.getNameBuilder();
			Path destinationDir = Paths.get(input[1]);

			if (Files.notExists(destinationDir)) {
				try {
					Files.createDirectory(destinationDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			directoryWalker(input[0], input[3], new BiConsumer<Path, Matcher>() {

				@Override
				public void accept(Path path, Matcher matcher) {
					BuilderInfo info = new BuilderInfo(matcher);
					System.out.print(input[0] + "\\" + path.toFile().getName() + SHOW_SEPARATOR + input[1] + "\\");
					builder.execute(info);

					Path sourceFile = Paths.get(input[0] + "\\" + path.toFile().getName());
					Path destinationFile = Paths.get(input[1] + "\\" + builder.getStringBuilder().toString());

					try {
						Files.move(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						System.out.println("File " + sourceFile.toString() + " already exist.");
					}

					System.out.println();
				}
			});

//			//@formatter:off
//			invokeGroupingWalker(input, builder, path -> { System.out.print(input[0] + "\\" + path.toFile().getName()
//														   + SHOW_SEPARATOR + input[1] + "\\");
//														   
////														   try {
////															Files.move(, REPLACE_EXISTING);
////														   } catch (IOException e) {
////															e.printStackTrace();
////														   }
//														 });
//
//			//@formatter:on

			break;

		default:
			env.writeln("Invalid massrename subcommand, was: " + input[2]);
			return TERMINATE;
		}

		return CONTINUE;
	}

	/**
	 * Helper method used for performing the invocation of the directory listing
	 * with required parameters.
	 *
	 * @param input
	 *            the user input
	 * @param builder
	 *            the builder class
	 * @param action
	 *            the action to be performed
	 */
	private void invokeGroupingWalker(String[] input, NameBuilder builder, Consumer<Path> action) {
		directoryWalker(input[0], input[3], new BiConsumer<Path, Matcher>() {

			@Override
			public void accept(Path path, Matcher matcher) {
				BuilderInfo info = new BuilderInfo(matcher);
				action.accept(path);
				builder.execute(info);
				System.out.println();
			}
		});
	}

	/**
	 * Validates massrename arguments.
	 *
	 * @param env
	 *            the reference to the object that makes communication between user,
	 *            shell and commands possible
	 * @param input
	 *            the input
	 * @param numOfRegexes
	 *            the num of regexes
	 * @return the shell status
	 */
	public ShellStatus validateMassrenameArguments(Environment env, String[] input, int numOfRegexes) {
		Path sourceDir = Paths.get(input[0]);

		if (Files.exists(sourceDir)) {

			if (Files.isDirectory(sourceDir)) {

				if (input.length == MANDATORY_ARGS_NUM + numOfRegexes) {

					while (numOfRegexes-- > 0) {
						if (!input[MANDATORY_ARGS_NUM + numOfRegexes].getClass().equals(String.class)) {
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

	/**
	 * Helper class that provides further information about the regular expression
	 * matching.
	 * 
	 * @author Damjan Vučina
	 */
	private static class BuilderInfo implements NameBuilderInfo {

		/** The matcher class. */
		private Matcher matcher;

		/**
		 * Instantiates a new builder info.
		 *
		 * @param matcher
		 *            the matcher class
		 */
		public BuilderInfo(Matcher matcher) {
			this.matcher = matcher;
		}

		/**
		 * Retrieves mapping for a specified regular expression group.
		 */
		@Override
		public String getGroup(int index) {
			return matcher.group(index);
		}

		@Override
		public StringBuilder getStringBuilder() {
			return null;
		}
	}
}
