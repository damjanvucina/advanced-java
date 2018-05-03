package hr.fer.zemris.java.hw07.shell;

import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.GROUP;
import static hr.fer.zemris.java.hw07.shell.NameBuilderParserState.NONGROUP;

import java.util.LinkedList;
import java.util.List;

/**
 * The class assigned with task of parsing user's grouping expression and
 * producing corresponding NameBuilderImplementations.
 * 
 * @author Damjan Vučina
 */
public class NameBuilderParser {

	/** The Constant SIMPLE_GROUP. */
	public static final String SIMPLE_GROUP = "\\d{1,}";

	/** The Constant COMPLEX_GROUP. */
	public static final String COMPLEX_GROUP = "\\d{1,},\\d{1,}";

	/** The regular expression defining groups to be parsed. */
	private char[] regex;

	/** The current position of the parser. */
	private int currentPosition;

	/** The length of the regular expression defining groups to be parsed. */
	private int length;

	/** The state of the parser. */
	private NameBuilderParserState state;

	/** The name builders. */
	private List<NameBuilder> nameBuilders;

	/**
	 * Instantiates a new name builder parser.
	 *
	 * @param regex
	 *            the regular expression defining groups to be parsed
	 */
	public NameBuilderParser(String regex) {
		this.regex = regex.toCharArray();

		currentPosition = 0;
		length = regex.length();
		state = NONGROUP;
		nameBuilders = new LinkedList<>();
	}

	/**
	 * Gets the regular expression
	 *
	 * @return the regular expression
	 */
	public String getRegex() {
		return String.valueOf(regex);
	}

	/**
	 * Gets the name builder.
	 *
	 * @return the name builder
	 */
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

	/**
	 * Processes non group state. Non group state is everything outside of this
	 * sequence: &{...} which defines the group state.
	 */
	private void processNonGroupState() {
		StringBuilder sb = new StringBuilder();

		while (currentPosition < length && regex[currentPosition] != '$'
				&& (currentPosition < length - 1 && regex[currentPosition + 1] != +'{')) {
			sb.append(regex[currentPosition++]);
		}

		if (currentPosition == length - 1) {
			sb.append(regex[currentPosition]);
		}

		if (sb.length() != 0) {
			nameBuilders.add(new NameBuilderPrinting(sb.toString()));
		}
		currentPosition += 2;// process group start
		state = GROUP;
	}

	/**
	 * Process group state which is defined by the &{...} sequence. The sequence
	 * contains a single number defining the specific regular expression group.It
	 * can also hold number defining the format of the group.
	 */
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
			String format = group.substring(groupSeparator + 1);

			nameBuilders.add(new NameBuilderGrouping(groupNumber, format));

		} else {
			throw new ShellIOException("Invalid grouping format");
		}

		currentPosition += 1; // process group end
		state = NONGROUP;
	}

	/**
	 * The Class NameBuilderPrinting which is assigned with the task of the printing
	 * the output segment it recieves via arguments to the console.
	 * 
	 * @author Damjan Vučina
	 */
	private static class NameBuilderPrinting implements NameBuilder {

		/** The output fragment. */
		private String fragment;

		/** The output segment. */
		private StringBuilder segment;

		/**
		 * Instantiates a new name builder printing.
		 *
		 * @param fragment
		 *            the fragment
		 */
		public NameBuilderPrinting(String fragment) {
			this.fragment = fragment;
		}

		/**
		 * 
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			segment = new StringBuilder();
			segment.append(fragment);
			System.out.print(fragment);
		}

		/**
		 * Prints the output segment it recieves via arguments to the console.
		 */
		@Override
		public StringBuilder getStringBuilder() {
			return segment;
		}
	}

	/**
	 * The Class NameBuilderPrinting which is assigned with the task of the printing
	 * the grouping segment it mapped to the number it recieves via arguments to the
	 * console.
	 * 
	 * @author Damjan Vučina
	 */
	private static class NameBuilderGrouping implements NameBuilder {

		/** The group number. */
		private int groupNumber;

		/** The format. */
		private String format;

		/** The segment. */
		private StringBuilder segment;

		/**
		 * Instantiates a new name builder grouping.
		 *
		 * @param groupNumber
		 *            the group number
		 * @param format
		 *            the format
		 */
		public NameBuilderGrouping(int groupNumber, String format) {
			this.groupNumber = groupNumber;
			this.format = format;
		}

		/**
		 * Prints the grouping segment it mapped to the number it recieves via arguments
		 * to the console.
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			String output;

			if (format.equals("")) {
				output = info.getGroup(groupNumber);
				segment = new StringBuilder();
				segment.append(output);

			} else {
				char paddingSymbol = format.charAt(0);
				int outputLength = Integer.parseInt(format.substring(1));
				String grouping = info.getGroup(groupNumber);
				StringBuilder sb = new StringBuilder();

				if (grouping.length() < outputLength) {
					sb.append(getSymbolSequence(paddingSymbol, outputLength - grouping.length()));
					sb.append(grouping);
				}

				segment = sb;
				output = sb.toString();
			}

			System.out.print(output);
		}

		/**
		 * Gets the symbol sequence.
		 *
		 * @param paddingSymbol
		 *            the padding symbol
		 * @param repetitions
		 *            the repetitions
		 * @return the symbol sequence
		 */
		private String getSymbolSequence(char paddingSymbol, int repetitions) {
			StringBuilder sb = new StringBuilder(repetitions);
			while (repetitions-- > 0) {
				sb.append(paddingSymbol);
			}

			return sb.toString();
		}

		/**
		 * Returns the grouping segment it mapped to the number it recieves via
		 * arguments.
		 * 
		 */
		@Override
		public StringBuilder getStringBuilder() {
			return segment;
		}
	}

	/**
	 * The Class NameBuilderPrinting which is assigned with the task of holding
	 * references to the other NameBuilder implementations. When its execute method
	 * is invoked, this class invokes execute methods of each NameBuilder
	 * implementation this class stores references to.
	 * 
	 * @author Damjan Vučina
	 */
	public static class NameBuilderReferencing implements NameBuilder {

		/** The name builders. */
		private List<NameBuilder> nameBuilders;

		/**
		 * Instantiates a new name builder referencing.
		 *
		 * @param nameBuilders
		 *            the name builders
		 */
		public NameBuilderReferencing(List<NameBuilder> nameBuilders) {
			this.nameBuilders = nameBuilders;
		}

		/**
		 * Invokes execute methods of each NameBuilder implementation this class stores
		 * references to.
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			for (NameBuilder builder : nameBuilders) {
				builder.execute(info);
			}
		}

		/**
		 * Returns the transformed file name.
		 */
		@Override
		public StringBuilder getStringBuilder() {
			StringBuilder sb = new StringBuilder();

			for (NameBuilder builder : nameBuilders) {
				sb.append(builder.getStringBuilder());
			}

			return sb;
		}
	}

}
