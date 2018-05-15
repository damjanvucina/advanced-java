package hr.fer.zemris.java.gui.charts;

import java.util.Arrays;
import java.util.List;

public class BarChart {

	List<XYValue> list;
	String xDescription;
	String yDescription;
	int minY;
	int maxY;
	int gapY;

	public BarChart(List<XYValue> list, String xDescription, String yDescription, int minY, int maxY, int gapY) {
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = validateMax(minY, maxY, gapY);
		this.gapY = gapY;
	}

	private int validateMax(int minY, int maxY, int gapY) {
		while ((maxY - minY) % gapY != 0) {
			maxY++;
		}

		return gapY;
	}

	public static void main(String[] args) {
		//@formatter:off
		BarChart model = new BarChart(Arrays.asList(new XYValue(1, 8),
													new XYValue(2, 20),
													new XYValue(3, 22),
													new XYValue(4, 10),
													new XYValue(5, 4)),
													"Number of people in the car",
													"Frequency",
													0, // y-os kreće od 0
													22, // y-os ide do 22
													2);
		//@formatter:on
	}
}
