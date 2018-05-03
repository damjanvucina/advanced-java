package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.MyShell.MORELINES;
import static hr.fer.zemris.java.hw07.shell.MyShell.MULTILINE;
import static hr.fer.zemris.java.hw07.shell.MyShell.PROMPT;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;

/**
 * The class that implements the interface Environment and as such provides the
 * methods for communication between user, MyShell class and various command
 * classes.
 * 
 * @author Damjan Vučina
 */
public class Dispatcher implements Environment {
	
	/** The Constant CD_STACK. */
	public static final String CD_STACK = "cdstack";

	/** The scanner used for reading from console. */
	Scanner sc;
	
	/** The current directory. */
	Path currentDirectory;
	
	/** The shared data. */
	Map<String, Object> sharedData;

	/**
	 * Instantiates a new dispatcher class used for communication between user,
	 * MyShell class and various command classes.
	 *
	 * @param sc
	 *            The scanner used for reading from console.
	 */
	public Dispatcher(Scanner sc) {
		this.sc = sc;
		currentDirectory = Paths.get(System.getProperty("user.dir"));
		sharedData = new HashMap<>();
		setUpSharedData();
	}

	/**
	 * Sets the up shared data, i.e. data that is shared between this shell command for optimization purposes..
	 */
	private void setUpSharedData() {
		Stack<Path> stack = new Stack<>();
		sharedData.put(CD_STACK, stack);
	}

	/**
	 * Method used for reading a new line from console if it exists.
	 *
	 * @return the string
	 * @throws ShellIOException             if this scanner is closed
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
	 * @param text the text
	 * @throws ShellIOException             if this scanner is closed
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
	 * @param text the text
	 * @throws ShellIOException             if this scanner is closed
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
	 *
	 * @param symbol the new multiline symbol
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
	 * @param symbol the new prompt symbol
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
	 * @param symbol the new morelines symbol
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MyShell.getSymbols().put(MORELINES, symbol);
	}

	/**
	 * Returns absolute path of the current java process.
	 *
	 * @return the absolute path of the current java process.
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * Sets the absolute path of the current java process.
	 *
	 * @param path the new absolute path of the current java process.
	 * @throws ShellIOException if provided path does not exist or if provided path is file path, not a directory path.
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if(Files.notExists(path)) {
			throw new ShellIOException("Provided path does not exist.");
			
		} else if(Files.isRegularFile(path)) {
			throw new ShellIOException("Provided path is file path, not a directory path.");
		}
		currentDirectory = path;
	}

	/**
	 * Gets the shared data, i.e. data that is shared between this shell command for optimization purposes.
	 *
	 * @param key the key of the shared data map
	 * @return the shared data
	 * @throws ShellIOException if element with null key is to be accessed from the shared data map
	 * 
	 */
	@Override
	public Object getSharedData(String key) {
		if(key == null) {
			throw new ShellIOException("Shared data does not store elements with null keys.");
		}
		
		return sharedData.get(key);
	}

	/**
	 * Sets the shared data, i.e. data that is shared between this shell command for optimization purposes.
	 *
	 * @param key the key of the shared data map
	 * @param value the value
	 * @throws ShellIOException if element with null key is to be put into the shared data map
	 */
	@Override
	public void setSharedData(String key, Object value) {
		if(key == null) {
			throw new ShellIOException("Shared data cannot store elements with null keys.");
		}
		
		sharedData.put(key, value);
	}

}
