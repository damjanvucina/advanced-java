package hr.fer.zemris.java.gui.charts;

/**
 * The class representing a point in the coordinate system.
 * 
 * @author Damjan Vučina
 */
public class XYValue {
	
	/** The x coordinate. */
	private int x;
	
	/** The y coordinate. */
	private int y;

	/**
	 * Instantiates a new XY value.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

}
