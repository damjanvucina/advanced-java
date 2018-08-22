package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * The Interface IColorProvider.
 */
public interface IColorProvider {
	
	/**
	 * Gets the current color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the color change listener.
	 *
	 * @param l the l
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the color change listener.
	 *
	 * @param l the l
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
