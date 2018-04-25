package hr.fer.zemris.java.hw06.shell;

import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
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
	
	public static SortedMap<String, ShellCommand> commands;
	public static Map<String, Character> symbols;
	public static Scanner sc;
	public static ShellStatus status;
	
	public static void main(String[] args) {
		initializeMyShell();
		printGreetingMessage();
		
		String line;
		while(status == CONTINUE) {
			System.out.print(symbols.get(PROMPT) + WHITESPACE);
			line = acquireNewLine();
			System.out.println(line);
		}
	}
	
	private static String acquireNewLine() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(sc.nextLine().trim());
		while(sb.toString().endsWith(String.valueOf(symbols.get(MORELINES)))) {
			sb.append(sc.nextLine().trim());
		}
		
		return sb.toString();
	}

	private static void printGreetingMessage() {
		System.out.println("Welcome to MyShell v 1.0");
	}

	private static void initializeMyShell() {
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		commands.put("symbol", new SymbolCommand());
		
		symbols.put("PROMPT", '>');
		symbols.put("MORELINES", '\\');
		symbols.put("MULTILINE", '|');
		
		sc = new Scanner(System.in);
		
		status = CONTINUE;
	}

	@Override
	public String readLine() throws ShellIOException {
		if(sc.hasNextLine()) {
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
