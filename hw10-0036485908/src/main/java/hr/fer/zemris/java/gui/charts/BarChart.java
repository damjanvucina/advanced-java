package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * The class that is used for the purpose of defining and storing information
 * about a certain BarChart. Such information includes list of graph
 * coordinates, axes descriptions and gaps.
 * 
 * @author Damjan Vučina
 */
public class BarChart {

	/** The coordinates to be drawn. */
	private List<XYValue> values;

	/** The x axis description. */
	private String xDescription;

	/** The y axis description. */
	private String yDescription;

	/** The min Y. */
	private int minY;

	/** The max Y. */
	private int maxY;

	/** The gap Y. */
	private int gapY;

	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values
	 *            the values
	 * @param xDescription
	 *            the x description
	 * @param yDescription
	 *            the y description
	 * @param minY
	 *            the min Y
	 * @param maxY
	 *            the max Y
	 * @param gapY
	 *            the gap Y
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int gapY) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = validateMax(minY, maxY, gapY);
		this.gapY = gapY;
	}

	/**
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the x description.
	 *
	 * @return the x description
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Gets the y description.
	 *
	 * @return the y description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Gets the min Y.
	 *
	 * @return the min Y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Gets the max Y.
	 *
	 * @return the max Y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Gets the gap Y.
	 *
	 * @return the gap Y
	 */
	public int getGapY() {
		return gapY;
	}

	/**
	 * Validates maximum y coordinate to be drawn, so that the subtraction of maxY
	 * and minY is divisible by the defined gap.
	 *
	 * @param minY
	 *            the min Y
	 * @param maxY
	 *            the max Y
	 * @param gapY
	 *            the gap Y
	 * @return the int
	 */
	private int validateMax(int minY, int maxY, int gapY) {
		while ((maxY - minY) % gapY != 0) {
			maxY++;
		}

		return maxY;
	}
}
