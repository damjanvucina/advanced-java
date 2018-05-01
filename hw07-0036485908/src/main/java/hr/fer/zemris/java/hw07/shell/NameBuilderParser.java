package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.GROUP;
import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.NONGROUP;

public class NameBuilderParser {
	
	private String regex;

	public NameBuilderParser(String regex) {
		this.regex = regex;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public NameBuilder getNameBuilder() {
		
		StringBuilder sb = new StringBuilder();
		int currentPosition = 0;
		int length = regex.length();
		NameBuilderParserState state = NONGROUP;
		
		while(currentPosition < length) {
			
			switch (state) {
			case NONGROUP:
				processNonGroupState();
				break;
				
			case GROUP:
				processGroupState();
				break;

			default:
				throw new ShellIOException("Invalid NameBuilderParserState");
			}
		}
	}

	private void processNonGroupState() {
		
	}

	private void processGroupState() {
		
	}
	
	
	
	

}
