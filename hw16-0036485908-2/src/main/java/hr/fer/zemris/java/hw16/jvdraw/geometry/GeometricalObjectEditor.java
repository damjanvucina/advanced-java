package hr.fer.zemris.java.hw16.jvdraw.geometry;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.model.ObjectModelException;

public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;

	public abstract void checkEditing();

	public abstract void acceptEditing();

	public void validateColorValue(String value) {
		int parsed = parse(value);
		if (parsed < 0 || parsed > 255) {
			throw new ObjectModelException("Invalid color value");
		}
	}

	public void validateCoordinates(String value) {
		int parsed = parse(value);
		if (parsed < 0) {
			throw new ObjectModelException("Invalid cooridinates");
		}
	}

	private int parse(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException | NullPointerException e) {
			return -1;
		}
	}

}
