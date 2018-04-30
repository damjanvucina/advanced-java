package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.ShellStatus.CONTINUE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;

/**
 * The class that represents a Shell. It provides the methods for communication
 * between user, MyShell class and various command classes. This class provides
 * user with following built-in commands: charsets, cat, ls, tree, copy, mkdir,
 * hexdump, symbol, help and exit. Command charsets lists names of supported
 * charsets for your Java platform. Command cat opens given file and writes its
 * content to console. Command ls takes a single argument – directory – and
 * writes a directory listing (not recursive). The tree command expects a single
 * argument: directory name and prints a tree for the files and the directories
 * within. The copy command copies given file to the provided location. The
 * mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure. The hexdump command expects a single
 * argument: file name, and produces hex-output. Symbol command is used for
 * changing the prompt, multilines or morelines symbol. The help method is used
 * for checking out the supported commands or for obtaining more detailed
 * information abot a specified command. The exit command is used for closing
 * this shell.
 * 
 * @author Damjan Vučina
 */
public class MyShell {

	/** The constant that defines the PROMPT symbol */
	public static final String PROMPT = "PROMPT";

	/** The constant that defines the MORELINES symbol */
	public static final String MORELINES = "MORELINES";

	/** The constant that defines the MULTILINE symbol */
	public static final String MULTILINE = "MULTILINE";

	/** The constant that defines a whitespace */
	public static final String WHITESPACE = " ";

	/** The constant that defines the cat command. */
	public static final String CAT_COMMAND = "cat";

	/** The constant that defines the charsets command. */
	public static final String CHARSETS_COMMAND = "charsets";

	/** The constant that defines the copy command. */
	public static final String COPY_COMMAND = "copy";

	/** The constant that defines the exit command. */
	public static final String EXIT_SHELL_COMMAND = "exit";

	/** The constant that defines the hexdumo command. */
	public static final String HEXDUMP_COMMAND = "hexdump";

	/** The constant that defines the ls command. */
	public static final String LS_COMMAND = "ls";

	/** The constant that defines the mkdir command. */
	public static final String MKDIR_COMMAND = "mkdir";

	/** The constant that defines the tree command. */
	public static final String TREE_COMMAND = "tree";

	/** The constant that defines the symbol command. */
	public static final String SYMBOL_COMMAND = "symbol";

	/** The constant that defines the help command. */
	public static final String HELP_COMMAND = "help";
	public static final String PWD_COMMAND = "pwd";
	public static final String CD_COMMAND = "cd";

	/**
	 * A map consisting of the all available commands suppoted by this MyShell.
	 */
	private static TreeMap<String, ShellCommand> commands = new TreeMap<>();

	/** A map consisting of the symbols currently in use by this MyShell */
	private static Map<String, Character> symbols = new HashMap<>();

	/** The scanner used for obtaining the user input. */
	private static Scanner sc;

	/** The current status of the shell. */
	private static ShellStatus status;

	/**
	 * The class that implements the interface Environment and as such provides the
	 * methods for communication between user, MyShell class and various command
	 * classes.
	 */
	private static Dispatcher dispatcher;

	/**
	 * Method used for accessing the map consisting of the all available commands
	 * supported by this MyShell.
	 * 
	 * @return unmodifiable map consisting of the all available commands supported
	 *         by this MyShell.
	 */
	public static TreeMap<String, ShellCommand> getCommands() {
		return commands;
	}

	/**
	 * Method used for accessing the map consisting of the symbols currently in use
	 * by this MyShell.
	 * 
	 * @return map consisting of the symbols currently in use by this MyShell
	 */
	public static Map<String, Character> getSymbols() {
		return symbols;
	}

	/**
	 * Sets the the map consisting of the all available commands supported by this
	 * MyShell.
	 *
	 * @param commands
	 *            commands supported by this MyShell.
	 */
	public static void setCommands(TreeMap<String, ShellCommand> commands) {
		MyShell.commands = commands;
	}

	/**
	 * Sets the map consisting of the symbols currently in use by this MyShell.
	 *
	 * @param symbols
	 *            the symbols currently in use by this MyShell
	 */
	public static void setSymbols(Map<String, Character> symbols) {
		MyShell.symbols = symbols;
	}

	/**
	 * The main method which is invoked when the program is run.
	 *
	 * @param args
	 *            the arguments. Notice: not used here
	 */
	public static void main(String[] args) {
		setUpMyShell();
		printGreetingMessage();

		String line;
		while (status == CONTINUE) {
			System.out.print(symbols.get(PROMPT) + WHITESPACE);

			line = acquireNewLine();
			processCommand(line.split(WHITESPACE));
		}
	}

	/**
	 * Method that identifies the entered command, fetches it from the map of
	 * commands supported by this MyShell, extracts arguments from the user input
	 * and initiates the process of executing that command. Available commands are:
	 * charsets, cat, ls, tree, copy, mkdir, hexdump, symbol, help and exit. Command
	 * charsets lists names of supported charsets for your Java platform. Command
	 * cat opens given file and writes its content to console. Command ls takes a
	 * single argument – directory – and writes a directory listing (not recursive).
	 * The tree command expects a single argument: directory name and prints a tree
	 * for the files and the directories within. The copy command copies given file
	 * to the provided location. The mkdir command takes a single argument:
	 * directory name, and creates the appropriate directory structure. The hexdump
	 * command expects a single argument: file name, and produces hex-output. Symbol
	 * command is used for changing the prompt, multilines or morelines symbol. The
	 * help method is used for checking out the supported commands or for obtaining
	 * more detailed information abot a specified command. The exit command is used
	 * for closing this shell.
	 *
	 * @param input
	 *            the user input
	 */
	private static void processCommand(String[] input) {
		ShellCommand command;

		switch (input[0]) {
		case SYMBOL_COMMAND:
			command = commands.get(SYMBOL_COMMAND);
			break;

		case CAT_COMMAND:
			command = commands.get(CAT_COMMAND);
			break;

		case CHARSETS_COMMAND:
			command = commands.get(CHARSETS_COMMAND);
			break;

		case COPY_COMMAND:
			command = commands.get(COPY_COMMAND);
			break;

		case EXIT_SHELL_COMMAND:
			command = commands.get(EXIT_SHELL_COMMAND);
			break;

		case HEXDUMP_COMMAND:
			command = commands.get(HEXDUMP_COMMAND);
			break;

		case LS_COMMAND:
			command = commands.get(LS_COMMAND);
			break;

		case MKDIR_COMMAND:
			command = commands.get(MKDIR_COMMAND);
			break;

		case TREE_COMMAND:
			command = commands.get(TREE_COMMAND);
			break;

		case HELP_COMMAND:
			command = commands.get(HELP_COMMAND);
			break;
			
		case PWD_COMMAND:
			command = commands.get(PWD_COMMAND);
			break;
			
		case CD_COMMAND:
			command = commands.get(CD_COMMAND);
			break;


		default:
			System.out.println("Invalid command, was: " + input[0]);
			return;
		}
		status = command.executeCommand(dispatcher, extractArguments(input));
	}

	/**
	 * Method that extracts the arguments from the user's query so they can be
	 * properly processed by the specified command.
	 *
	 * @param input
	 *            the user's input query
	 * @return the string consisting of concatenated query arguments separated by
	 *         the appended whitespace
	 */
	private static String extractArguments(String[] input) {
		String[] arguments = Arrays.copyOfRange(input, 1, input.length);

		StringBuilder sb = new StringBuilder();
		for (String arg : arguments) {
			sb.append(arg);
			sb.append(WHITESPACE);
		}

		return sb.toString();

	}

	/**
	 * Method used for obtaining new query or lines of the multi-line query from the
	 * user via console.
	 *
	 * @return the string representing new query or lines of the multi-line query
	 */
	private static String acquireNewLine() {
		StringBuilder sb = new StringBuilder();

		sb.append(sc.nextLine().trim());
		while (sb.toString().endsWith(String.valueOf(symbols.get(MORELINES)))) {
			System.out.print(symbols.get(MULTILINE) + WHITESPACE);
			sb.deleteCharAt(sb.length() - 1);
			sb.append(sc.nextLine().trim());
		}

		return sb.toString();
	}

	/**
	 * Method that prints the greeting message to the console.
	 */
	private static void printGreetingMessage() {
		System.out.println("Welcome to MyShell v 1.0");
	}

	/**
	 * Sets the up MyShell by instantiating Dispatcher class, Scanner class and
	 * filling maps containing the shell's commands and symbols.
	 */
	private static void setUpMyShell() {
		commands.put(CAT_COMMAND, new CatCommand());
		commands.put(CHARSETS_COMMAND, new CharsetsCommand());
		commands.put(COPY_COMMAND, new CopyCommand());
		commands.put(EXIT_SHELL_COMMAND, new ExitShellCommand());
		commands.put(HEXDUMP_COMMAND, new HexdumpCommand());
		commands.put(LS_COMMAND, new LsCommand());
		commands.put(MKDIR_COMMAND, new MkdirCommand());
		commands.put(TREE_COMMAND, new TreeCommand());
		commands.put(SYMBOL_COMMAND, new SymbolCommand());
		commands.put(HELP_COMMAND, new HelpCommand());
		commands.put(PWD_COMMAND, new PwdCommand());
		commands.put(CD_COMMAND, new CdCommand());

		symbols.put(PROMPT, '>');
		symbols.put(MORELINES, '\\');
		symbols.put(MULTILINE, '|');

		sc = new Scanner(System.in);

		dispatcher = new Dispatcher(sc);

		status = CONTINUE;
	}
}
