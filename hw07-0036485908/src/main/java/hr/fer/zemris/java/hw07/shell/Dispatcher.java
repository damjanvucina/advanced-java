package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.MyShell.MORELINES;
import static hr.fer.zemris.java.hw07.shell.MyShell.MULTILINE;
import static hr.fer.zemris.java.hw07.shell.MyShell.PROMPT;

import java.util.Scanner;
import java.util.SortedMap;

/**
 * The class that implements the interface Environment and as such provides the
 * methods for communication between user, MyShell class and various command
 * classes.
 * 
 * @author Damjan Vučina
 */
public class Dispatcher implements Environment {

	/** The scanner used for reading from console. */
	Scanner sc;

	/**
	 * Instantiates a new dispatcher class used for communication between user,
	 * MyShell class and various command classes.
	 *
	 * @param sc
	 *            The scanner used for reading from console.
	 */
	public Dispatcher(Scanner sc) {
		this.sc = sc;
	}

	/**
	 * Method used for reading a new line from console if it exists.
	 * 
	 * @throws ShellIOException
	 *             if this scanner is closed
	 */
	@Override
	public String readLine() {
		try {
			if (sc.hasNextLine()) {
				return sc.nextLine().trim();
			}
		} catch (ShellIOException e) {
			System.out.println("This scanner is closed");
		}
		return null;
	}

	/**
	 * Method used for writing a new chunk of text to the console. Unlike writeln
	 * method this method does not put CR/LF at the end of the output.
	 * 
	 * @throws ShellIOException
	 *             if this scanner is closed
	 */
	@Override
	public void write(String text) {
		try {
			System.out.print(text);
		} catch (ShellIOException e) {
			System.out.println("Error writing to console occured.");
		}
	}

	/**
	 * Method used for writing a new chunk of text to the console. Unlike write
	 * method this method does put CR/LF at the end of the output.
	 * 
	 * @throws ShellIOException
	 *             if this scanner is closed
	 */

	@Override
	public void writeln(String text) {
		try {
			System.out.println(text);
		} catch (ShellIOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used for accessing the map consisting of the all available commands
	 * suppoted by this MyShell.
	 * 
	 * @return unmodifiable map consisting of the all available commands suppoted by
	 *         this MyShell.
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return MyShell.getCommands();
	}

	/**
	 * Gets the symbol which is currently in use as multiline symbol. For each line
	 * that is part of multi-line command (except for the first one) this shell
	 * writes MULTILINESYMBOL at the beginning followed by a single whitespace.
	 * 
	 * @return the symbol which is currently in use as multiline symbol.
	 */
	@Override
	public Character getMultilineSymbol() {
		return MyShell.getSymbols().get(MULTILINE);
	}

	/**
	 * Sets the symbol which is currently in use as multiline symbol. For each line
	 * that is part of multi-line command (except for the first one) this shell
	 * writes MULTILINESYMBOL at the beginning followed by a single whitespace.
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		MyShell.getSymbols().put(MULTILINE, symbol);
	}

	/**
	 * Gets the symbol which is currently in use as prompt symbol. Whenever the
	 * MyShell expect some user input it writes prompt symbol to the console to
	 * inform the user about it.
	 * 
	 * @return the symbol which is currently in use as prompt symbol.
	 */
	@Override
	public Character getPromptSymbol() {
		return MyShell.getSymbols().get(PROMPT);
	}

	/**
	 * Sets the symbol which is currently in use as prompt symbol. Whenever the
	 * MyShell expect some user input it writes prompt symbol to the console to
	 * inform the user about it.
	 * 
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		MyShell.getSymbols().put(PROMPT, symbol);
	}

	/**
	 * Gets the symbol which is currently in use as morelines symbol. Morelines
	 * symbol is the symbol user types at the end of the line informing the MyShell
	 * that the query is not yet finished and new furhter arguments are to be
	 * provided.
	 * 
	 * @return the symbol which is currently in use as morelines symbol.
	 */
	@Override
	public Character getMorelinesSymbol() {
		return MyShell.getSymbols().get(MORELINES);
	}

	/**
	 * Sets the symbol which is currently in use as morelines symbol. Morelines
	 * symbol is the symbol user types at the end of the line informing the MyShell
	 * that the query is not yet finished and new furhter arguments are to be
	 * provided.
	 * 
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MyShell.getSymbols().put(MORELINES, symbol);
	}

}
