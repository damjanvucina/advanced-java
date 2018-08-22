package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

// TODO: Auto-generated Javadoc
/**
 * The Class GeometricalObjectEditor.
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant COLOR_VALIDATOR. */
	private static final String COLOR_VALIDATOR = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})";
	
	/** The pattern. */
	private Pattern pattern = Pattern.compile(COLOR_VALIDATOR);

	/**
	 * Check editing.
	 */
	public abstract void checkEditing();

	/**
	 * Accept editing.
	 */
	public abstract void acceptEditing();

	/**
	 * Validate color value.
	 *
	 * @param value the value
	 */
	public void validateColorValue(String value) {
		boolean matches = pattern.matcher(value).matches();
		if (!matches) {
			throw new ObjectModelException("Invalid color string");
		}
	}

	/**
	 * Validate coordinates.
	 *
	 * @param value the value
	 */
	public void validateCoordinates(String value) {
		int parsed = parse(value);
		if (parsed < 0) {
			throw new ObjectModelException("Invalid cooridinates");
		}
	}

	/**
	 * Validate radius.
	 *
	 * @param value the value
	 */
	public void validateRadius(String value) {
		int radius = parse(value);
		if (radius <= 0) {
			throw new ObjectModelException("Invalid radius");
		}
	}
	
	/**
	 * Color to hex string.
	 *
	 * @param color the color
	 * @return the string
	 */
	protected String colorToHexString(Color color) {
		return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Parses the.
	 *
	 * @param value the value
	 * @return the int
	 */
	private int parse(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException e) {
			return -1;
		}
	}

}