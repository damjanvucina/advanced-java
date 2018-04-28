package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import static hr.fer.zemris.java.hw06.shell.MyShell.*;

/**
 * The command that is used for changing the prompt, multilines or morelines
 * symbol.
 * 
 * @author Damjan Vučina
 */
public class SymbolCommand extends Command {

	/**
	 * Instantiates a new symbol command and provides description that can later be
	 * obtained via help command.
	 */
	public SymbolCommand() {
		super("symbol", Arrays.asList("Command used for viewing and editing symbols.",
				"Editable symbols are PROMPT, MORELINES and MULTILINE."));
	}

	/**
	 * Method that prints out the symbol for the specified symbol or updated the
	 * specified symbol with the newly provided character in the arguments
	 * 
	 * @return ShellStatus The enum that defines the result of the execution of the
	 *         specified command. MyShell program will end by terminating only if
	 *         there is no way of recovering from the user's invalid input.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = splitArguments(arguments);
		if (input.length < 1 || input.length > 2) {
			env.writeln("Command " + getCommandName() + " takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}

		switch (input[0]) {
		case PROMPT:

			if (input.length == 1) {
				printSymbol(PROMPT, env.getPromptSymbol(), env);
			} else {
				Character oldCharacter = env.getPromptSymbol();
				Character newCharacter = input[1].charAt(0);
				env.setPromptSymbol(newCharacter);
				printUpdatedSymbol(PROMPT, oldCharacter, newCharacter, env);
			}
			break;

		case MORELINES:

			if (input.length == 1) {
				printSymbol(MORELINES, env.getMorelinesSymbol(), env);
			} else {
				Character oldCharacter = env.getMorelinesSymbol();
				Character newCharacter = input[1].charAt(0);
				env.setMorelinesSymbol(newCharacter);
				printUpdatedSymbol(MORELINES, oldCharacter, newCharacter, env);
			}
			break;

		case MULTILINE:

			if (input.length == 1) {
				printSymbol(MULTILINE, env.getMultilineSymbol(), env);
			} else {
				Character oldCharacter = env.getMultilineSymbol();
				Character newCharacter = input[1].charAt(0);
				env.setMultilineSymbol(newCharacter);
				printUpdatedSymbol(MORELINES, oldCharacter, newCharacter, env);
			}
			break;

		default:
			env.writeln("Invalid symbol, was: " + input[0]);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints the updated symbol.
	 *
	 * @param symbol
	 *            the symbol
	 * @param oldCharacter
	 *            the old character
	 * @param newCharacter
	 *            the new character
	 * @param env
	 *            the reference to the object that is assigned with the task of
	 *            making the communication between the user, shell and specific
	 *            command possible and uniform.
	 */
	private void printUpdatedSymbol(String symbol, Character oldCharacter, Character newCharacter, Environment env) {
		env.writeln("Symbol for " + symbol + " changed from '" + oldCharacter + "' to '" + newCharacter + "'");
	}

	/**
	 * Prints the symbol.
	 *
	 * @param symbol
	 *            the symbol
	 * @param character
	 *            the character
	 * @param env
	 *            the reference to the object that is assigned with the task of
	 *            making the communication between the user, shell and specific
	 *            command possible and uniform.
	 */
	private void printSymbol(String symbol, Character character, Environment env) {
		env.writeln("Symbol for " + symbol + " is '" + character + "'");
	}

}
