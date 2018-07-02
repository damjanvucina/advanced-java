package hr.fer.zemris.java.hw16.jvdraw.geometry;

import javax.swing.JPanel;

public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;

	public abstract void checkEditing();

	public abstract void acceptEditing();
}
