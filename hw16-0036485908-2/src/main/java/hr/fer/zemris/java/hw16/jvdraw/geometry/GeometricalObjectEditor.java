package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final String COLOR_VALIDATOR = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})";
	private Pattern pattern = Pattern.compile(COLOR_VALIDATOR);

	public abstract void checkEditing();

	public abstract void acceptEditing();

	public void validateColorValue(String value) {
		boolean matches = pattern.matcher(value).matches();
		if (!matches) {
			throw new ObjectModelException("Invalid color string");
		}
	}

	public void validateCoordinates(String value) {
		int parsed = parse(value);
		if (parsed < 0) {
			throw new ObjectModelException("Invalid cooridinates");
		}
	}

	public void validateRadius(String value) {
		int radius = parse(value);
		if (radius <= 0) {
			throw new ObjectModelException("Invalid radius");
		}
	}
	
	protected String colorToHexString(Color color) {
		return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

	private int parse(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException e) {
			return -1;
		}
	}

}