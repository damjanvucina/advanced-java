package hr.fer.zemris.java.gui.layouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class that is used for the purpose of defining a coordinate position (row
 * and column), making for easier drawing of the components in calculator and
 * chart classes.
 * 
 * @author Damjan Vučina
 */
public class RCPosition {

	/** The Constant RCPOSITION_VALIDATOR. */
	private static final String RCPOSITION_VALIDATOR = "(\\d){1},(\\d){1}";

	/** The Constant WHITESPACE. */
	private static final String WHITESPACE = " ";

	/** The Constant EMPTY_STRING. */
	private static final String EMPTY_STRING = "";

	/** The Constant EXTRACT_ROW. */
	private static final int EXTRACT_ROW = 1;

	/** The Constant EXTRACT_COL. */
	private static final int EXTRACT_COL = 2;

	/** The row of the component. */
	private int row;

	/** The column of the component. */
	private int column;

	/**
	 * The pattern used for determining whether the string format of the RCPosition
	 * is in compliance with the specified format.
	 */
	private static Pattern pattern = Pattern.compile(RCPOSITION_VALIDATOR);;

	/**
	 * Instantiates a new RC position.
	 *
	 * @param row
	 *            the row of the component
	 * @param column
	 *            the column of the component
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Gets the row of the component.
	 *
	 * @return the row of the component
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column of the component.
	 *
	 * @return the column of the component
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Checks if two instances of RCPosition class are equal by calculating their
	 * hash. Two instances of RCPosition class are considered equal if they have
	 * identical row and column attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * Checks if two instances of RCPosition class are equal. Two instances of
	 * RCPosition class are considered equal if they have identical row and column
	 * attributes.
	 */
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

	/**
	 * Extracts RC position from string format which is in compliance with the
	 * specified format.
	 *
	 * @param position
	 *            the string representation of the RCPosition
	 * @return the RC position
	 * @throws CalcLayoutException
	 *             if position is null or position is empty string or invalid String
	 *             representation of RCPosition is given
	 */
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
}
