package hr.fer.zemris.java.gui.charts;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;

	BarChart chart;

	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
}
