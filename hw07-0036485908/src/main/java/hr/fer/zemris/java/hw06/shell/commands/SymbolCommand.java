package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import static hr.fer.zemris.java.hw06.shell.MyShell.*;

public class SymbolCommand extends Command {

	public SymbolCommand() {
		super("symbol", Arrays.asList("Command used for viewing and editing symbols.",
				"Editable symbols are PROMPT, MORELINES and MULTILINE."));
	}

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

	private void printUpdatedSymbol(String symbol, Character oldCharacter, Character newCharacter, Environment env) {
		env.writeln("Symbol for " +  symbol + " changed from '" + oldCharacter + "' to '" + newCharacter + "'");
	}

	private void printSymbol(String symbol, Character character, Environment env) {
		env.writeln("Symbol for " + symbol + " is '" + character + "'");
	}

}
