package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * The interface that provides methods for communication between user, MyShell
 * class and various command classes.
 * 
 * @author Damjan Vučina
 */
public interface Environment {

	/**
	 * Read line.
	 *
	 * @return the string
	 * @throws ShellIOException
	 *             the shell IO exception
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method used for writing a new chunk of text to the console. Unlike writeln
	 * method this method does not put CR/LF at the end of the output.
	 * 
	 * @param text
	 *            the text
	 * @throws ShellIOException
	 *             if this scanner is closed
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method used for writing a new chunk of text to the console. Unlike write
	 * method this method does put CR/LF at the end of the output.
	 *
	 * @param text
	 *            the text
	 * @throws ShellIOException
	 *             if this scanner is closed
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Method used for accessing the map consisting of the all available commands
	 * suppoted by this MyShell.
	 * 
	 * @return unmodifiable map consisting of the all available commands suppoted by
	 *         this MyShell.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Gets the symbol which is currently in use as multiline symbol. For each line
	 * that is part of multi-line command (except for the first one) this shell
	 * writes MULTILINESYMBOL at the beginning followed by a single whitespace.
	 * 
	 * @return the symbol which is currently in use as multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the symbol which is currently in use as multiline symbol. For each line
	 * that is part of multi-line command (except for the first one) this shell
	 * writes MULTILINESYMBOL at the beginning followed by a single whitespace.
	 * 
	 * @param symbol
	 *            the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets the symbol which is currently in use as prompt symbol. Whenever the
	 * MyShell expect some user input it writes prompt symbol to the console to
	 * inform the user about it.
	 * 
	 * @return the symbol which is currently in use as prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets the symbol which is currently in use as prompt symbol. Whenever the
	 * MyShell expect some user input it writes prompt symbol to the console to
	 * inform the user about it.
	 * 
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets the symbol which is currently in use as morelines symbol. Morelines
	 * symbol is the symbol user types at the end of the line informing the MyShell
	 * that the query is not yet finished and new furhter arguments are to be
	 * provided.
	 * 
	 * 	 * @param symbol
	 *            the new promt symbol
	 * 
	 * @return the symbol which is currently in use as morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the symbol which is currently in use as morelines symbol. Morelines
	 * symbol is the symbol user types at the end of the line informing the MyShell
	 * that the query is not yet finished and new furhter arguments are to be
	 * provided.
	 * 
	 * 	 * @param symbol
	 *            the new morelines symbol
	 * 
	 */
	void setMorelinesSymbol(Character symbol);
	Path getCurrentDirectory();
	void setCurrentDirectory(Path path);
	Object getSharedData(String key);
	void setSharedData(String key, Object value);
}
