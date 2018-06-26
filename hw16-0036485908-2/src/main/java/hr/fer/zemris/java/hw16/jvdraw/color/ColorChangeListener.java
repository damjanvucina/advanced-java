package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

public interface ColorChangeListener {
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
