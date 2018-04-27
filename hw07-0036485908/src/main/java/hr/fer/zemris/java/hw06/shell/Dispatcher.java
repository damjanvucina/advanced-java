package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import static hr.fer.zemris.java.hw06.shell.MyShell.PROMPT;
import static hr.fer.zemris.java.hw06.shell.MyShell.MULTILINE;
import static hr.fer.zemris.java.hw06.shell.MyShell.MORELINES;

public class Dispatcher implements Environment {
	Scanner sc;

	public Dispatcher(Scanner sc) {
		this.sc = sc;
	}

	@Override
	public String readLine() {
		try {
			if (sc.hasNextLine()) {
				return sc.nextLine().trim();
			}
		} catch (ShellIOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void write(String text) {
		try {
			System.out.print(text);
		} catch (ShellIOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeln(String text) {
		try {
			System.out.println(text);
		} catch (ShellIOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return MyShell.getCommands();
	}

	@Override
	public Character getMultilineSymbol() {
		return MyShell.getSymbols().get(MULTILINE);
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MyShell.getSymbols().put(MULTILINE, symbol);
	}

	@Override
	public Character getPromptSymbol() {
		return MyShell.getSymbols().get(PROMPT);
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		MyShell.getSymbols().put(PROMPT, symbol);
	}

	@Override
	public Character getMorelinesSymbol() {
		return MyShell.getSymbols().get(MORELINES);
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MyShell.getSymbols().put(MORELINES, symbol);
	}

}
