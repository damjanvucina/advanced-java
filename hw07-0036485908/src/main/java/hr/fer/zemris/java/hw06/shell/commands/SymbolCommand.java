package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand extends Command {

	public SymbolCommand() {
		super("symbol", Arrays.asList("Command used for editing editable symbols.",
									  "Editable symbols are PROMPT, MORELINES and MULTILINE."));
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] input = arguments.split(" ");
		if(input.length < 1 || input.length > 2) {
			env.writeln("Command " + getCommandName() + " takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if(input[1].length() > 1) {
			env.writeln("Symbol must be a single character, was: " + input[1]);
		}
				
		Character character = input[1].charAt(0);
		switch (input[0]) {
		case "PROMPT":
			
			if(input.length == 1) {
				env.write(String.valueOf(env.getPromptSymbol()));
			} else {
				env.setPromptSymbol(character);
			}
			break;
			
		case "MORELINES":
			
			if(input.length == 1) {
				env.write(String.valueOf(env.getMorelinesSymbol()));
			} else {
				env.setMorelinesSymbol(character);
			}
			break;
			
		case "MULTILINE":
			
			if(input.length == 1) {
				env.write(String.valueOf(env.getMultilineSymbol()));
			} else {
				env.setMultilineSymbol(character);
			}
			break;

		default:
			env.writeln("Invalid symbol, was: " + input[0]);
		}
		
		return ShellStatus.CONTINUE;
		
	}


}
