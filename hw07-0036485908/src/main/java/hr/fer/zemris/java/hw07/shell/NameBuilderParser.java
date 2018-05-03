package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.GROUP;
import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.NONGROUP;

import java.util.LinkedList;
import java.util.List;

public class NameBuilderParser {
	public static final String SIMPLE_GROUP = "\\d{1,}";
	public static final String COMPLEX_GROUP = "\\d{1,},\\d{1,}";

	private char[] regex;
	private int currentPosition;
	private int length;
	private NameBuilderParserState state;
	private List<NameBuilder> nameBuilders;

	public NameBuilderParser(String regex) {
		this.regex = regex.toCharArray();

		currentPosition = 0;
		length = regex.length();
		state = NONGROUP;
		nameBuilders = new LinkedList<>();
	}

	public String getRegex() {
		return String.valueOf(regex);
	}

	public NameBuilder getNameBuilder() {

		while (currentPosition < length) {

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
		
		return new NameBuilderReferencing(nameBuilders);
	}

	private void processNonGroupState() {
		StringBuilder sb = new StringBuilder();

		while (currentPosition < length && regex[currentPosition] != '$' && (currentPosition < length-1 && regex[currentPosition + 1] != +'{')) {
			sb.append(regex[currentPosition++]);
		}
		
		if(currentPosition == length-1) {
			sb.append(regex[currentPosition]);
		}

		if (sb.length() != 0) {
			nameBuilders.add(new NameBuilderPrinting(sb.toString()));
		}
		currentPosition += 2;// process group start
		state = GROUP;
	}

	private void processGroupState() {
		StringBuilder sb = new StringBuilder();

		while (currentPosition < length && regex[currentPosition] != +'}') {
			if (Character.isWhitespace(regex[currentPosition])) {
				currentPosition++;
				continue;
			}
			sb.append(regex[currentPosition++]);
		}

		String group = sb.toString();
		if (group.matches(SIMPLE_GROUP)) {
			nameBuilders.add(new NameBuilderGrouping(Integer.parseInt(group), ""));
			
		} else if (group.matches(COMPLEX_GROUP)) {
			int groupSeparator = group.indexOf(",");
			int groupNumber = Integer.parseInt(group.substring(0, groupSeparator));
			String format = group.substring(groupSeparator+1);
			
			nameBuilders.add(new NameBuilderGrouping(groupNumber, format));
			
		} else {
			throw new ShellIOException("Invalid grouping format");
		}

		currentPosition+=1; //process group end
		state = NONGROUP;
	}

	private static class NameBuilderPrinting implements NameBuilder {
		private String fragment;
		private StringBuilder segment;

		public NameBuilderPrinting(String fragment) {
			this.fragment = fragment;
		}

		@Override
		public void execute(NameBuilderInfo info) {
			segment = new StringBuilder();
			segment.append(fragment);
			System.out.print(fragment);
		}

		@Override
		public StringBuilder getStringBuilder() {
			return segment;
		}
	}

	private static class NameBuilderGrouping implements NameBuilder {
		private int groupNumber;
		private String format;
		private StringBuilder segment;
		
		public NameBuilderGrouping(int groupNumber, String format) {
			this.groupNumber = groupNumber;
			this.format = format;
		}
		
		@Override
		public void execute(NameBuilderInfo info) {
			String output;
			
			if(format.equals("")) {
				output = info.getGroup(groupNumber);
				segment = new StringBuilder();
				segment.append(output);
				
			} else {
				char paddingSymbol = format.charAt(0);
				int outputLength = Integer.parseInt(format.substring(1));
				String grouping = info.getGroup(groupNumber);
				StringBuilder sb = new StringBuilder();
				
				if(grouping.length() < outputLength) {
					sb.append(getSymbolSequence(paddingSymbol, outputLength - grouping.length()));
					sb.append(grouping);
				}
				
				segment = sb;
				output = sb.toString();
			}
			
			System.out.print(output);
		}

		private String getSymbolSequence(char paddingSymbol, int repetitions) {
			StringBuilder sb = new StringBuilder(repetitions);
			while(repetitions-- > 0) {
				sb.append(paddingSymbol);
			}
			
			return sb.toString();
		}

		@Override
		public StringBuilder getStringBuilder() {
			return segment;
		}
	}

	public static class NameBuilderReferencing implements NameBuilder {
		private List<NameBuilder> nameBuilders;
		
		public NameBuilderReferencing(List<NameBuilder> nameBuilders) {
			this.nameBuilders = nameBuilders;
		}

		@Override
		public void execute(NameBuilderInfo info) {
			for(NameBuilder builder : nameBuilders) {
				builder.execute(info);
			}
		}

		@Override
		public StringBuilder getStringBuilder() {
			StringBuilder sb = new StringBuilder();
			
			for(NameBuilder builder : nameBuilders) {
				sb.append(builder.getStringBuilder());
			}
			
			return sb;
		}
	}

}
