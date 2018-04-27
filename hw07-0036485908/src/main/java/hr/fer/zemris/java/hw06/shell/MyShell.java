package hr.fer.zemris.java.hw06.shell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

import static hr.fer.zemris.java.hw06.shell.ShellStatus.*;

public class MyShell implements Environment {
	public static final String PROMPT = "PROMPT";
	public static final String MORELINES = "MORELINES";
	public static final String MULTILINE = "MULTILINE";
	public static final String WHITESPACE = " ";
	public static final String CAT_COMMAND = "cat";
	public static final String CHARSETS_COMMAND = "charsets";
	public static final String COPY_COMMAND = "copy";
	public static final String EXIT_SHELL_COMMAND = "exit";
	public static final String HEXDUMP_COMMAND = "hexdump";
	public static final String LS_COMMAND = "ls";
	public static final String MKDIR_COMMAND = "mkdir";
	public static final String TREE_COMMAND = "tree";
	public static final String SYMBOL_COMMAND = "symbol";
	public static final String HELP_COMMAND = "help";

	private static TreeMap<String, ShellCommand> commands = new TreeMap<>();
	private static Map<String, Character> symbols = new HashMap<>();
	private static Scanner sc;
	private static ShellStatus status;
	private static MyShell dispatcher;;

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

		default:
			System.out.println("Invalid command, was: " + input[0]);
			return;
		}
		status = command.executeCommand(dispatcher, extractArguments(input));
	}

	private static String extractArguments(String[] input) {
		String[] arguments = Arrays.copyOfRange(input, 1, input.length);

		StringBuilder sb = new StringBuilder();
		for (String arg : arguments) {
			sb.append(arg);
			sb.append(WHITESPACE);
		}

		return sb.toString();

	}

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

	private static void printGreetingMessage() {
		System.out.println("Welcome to MyShell v 1.0");
	}

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

		symbols.put(PROMPT, '>');
		symbols.put(MORELINES, '\\');
		symbols.put(MULTILINE, '|');

		sc = new Scanner(System.in);

		dispatcher = new MyShell();

		status = CONTINUE;
	}

	@Override
	public String readLine() throws ShellIOException {
		if (sc.hasNextLine()) {
			return sc.nextLine().trim();
		}

		return null;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return symbols.get(MULTILINE);
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		symbols.put(MULTILINE, symbol);
	}

	@Override
	public Character getPromptSymbol() {
		return symbols.get(PROMPT);
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		symbols.put(PROMPT, symbol);
	}

	@Override
	public Character getMorelinesSymbol() {
		return symbols.get(MORELINES);
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		symbols.put(MORELINES, symbol);
	}

}
