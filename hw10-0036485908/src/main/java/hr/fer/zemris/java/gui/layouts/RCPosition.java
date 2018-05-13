package hr.fer.zemris.java.gui.layouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RCPosition {
	private static final String RCPOSITION_VALIDATOR = "(\\d){1},(\\d){1}";
	private static final String WHITESPACE = " ";
	private static final String EMPTY_STRING = "";
	private static final int EXTRACT_ROW = 1;
	private static final int EXTRACT_COL = 2;

	private int row;
	private int column;
	private static Pattern pattern = Pattern.compile(RCPOSITION_VALIDATOR);;

	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	public static RCPosition extractRCPosition(String position) {
		if (position == null) {
			throw new CalcLayoutException("RCPosition cannot be null");
		}

		if (EMPTY_STRING.equals(position)) {
			throw new CalcLayoutException("RCPosition cannot be empty string");
		}

		position = position.replace(WHITESPACE, EMPTY_STRING);
		Matcher matcher = pattern.matcher(position);

		if (!matcher.matches()) {
			throw new CalcLayoutException("Invalid String representation of RCPosition, was: " + position);
		}

		//@formatter:off
		return new RCPosition(Integer.parseInt(matcher.group(EXTRACT_ROW)),
							  Integer.parseInt(matcher.group(EXTRACT_COL)));
		//@formatter:on
	}

//	public static void main(String[] args) {
//		RCPosition position = extractRCPosition(" 1 , 2");
//		System.out.println(position.getRow());
//		System.out.println(position.getColumn());
//	}

}
