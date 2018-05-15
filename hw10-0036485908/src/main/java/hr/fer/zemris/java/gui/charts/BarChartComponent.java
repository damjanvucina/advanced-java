package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JComponent;

import hr.fer.zemris.java.gui.layouts.RCPosition;

public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private static final int AXIS_SMALL_GAP = 10;
	private static final int AXIS_BIG_GAP = 20;
	private static final int ZERO_GAP = 3;
	private static final Font DESCRIPTION_FONT = new Font("Arial", Font.PLAIN, 20);
	private static final Font COORDINATES_FONT = new Font("Arial", Font.PLAIN, 15);
	private static final Color BAR_COLOR = Color.decode("#f4b8a1");

	private BarChart chart;
	private Graphics2D g2d;

	private XYValue point00;
	private XYValue point01;
	private XYValue point10;
	private XYValue point11;

	public BarChartComponent(BarChart chart) {
		this.chart = chart;

		initGui();
	}

	private void initGui() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g2d = (Graphics2D) g;

		intializeGraphEndPoints();

		drawYAxis();
		drawXAxis();

		drawVerticalGridLines();
		drawHorizontalGridLines();


		drawXAxisDescription();
		drawYAxisDescription();
	}

	private void intializeGraphEndPoints() {
		int x1 = 2 * AXIS_BIG_GAP + AXIS_SMALL_GAP + g2d.getFontMetrics(DESCRIPTION_FONT).getAscent() - ZERO_GAP;
		int y1 = getHeight() - 2 * AXIS_BIG_GAP - g2d.getFontMetrics(DESCRIPTION_FONT).getAscent();
		int x2 = getWidth() - AXIS_BIG_GAP;
		int y2 = y1;
		int x3 = x1;
		int y3 = AXIS_BIG_GAP;

		point00 = new XYValue(x1, y1);
		point01 = new XYValue(x2, y2);
		point10 = new XYValue(x3, y3);
		point11 = new XYValue(x2, y3);
	}

	private void drawHorizontalGridLines() {
		int numOfSpaces = (chart.getMaxY() - chart.getMinY()) / chart.getGapY();
		int barLength = (int) Math.hypot(point00.getX() - point10.getX(), point00.getY() - point10.getY())
				/ numOfSpaces;

		g2d.setColor(BAR_COLOR);
		for (int i = 1; i <= numOfSpaces; i++) {
			g2d.drawLine(point00.getX(), point00.getY() - i * barLength + ZERO_GAP, point01.getX(),
					point01.getY() - i * barLength + ZERO_GAP);
		}

		g2d.setColor(Color.BLACK);
	}

	private void drawVerticalGridLines() {
		int numOfBars = chart.getValues().size();
		int barLength = (int) Math.hypot(point00.getX() - point01.getX(), point00.getY() - point01.getY()) / numOfBars;

		g2d.setColor(BAR_COLOR);
		for (int i = 1; i <= numOfBars; i++) {
			g2d.drawLine(point00.getX() + i * barLength, point00.getY() + ZERO_GAP, point10.getX() + i * barLength,
					point10.getY());
		}
		g2d.setColor(Color.BLACK);
	}

	//	//@formatter:on
	private void drawXAxis() {
		Stroke defaultStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.GRAY);

		g2d.drawLine(point00.getX() - ZERO_GAP, point00.getY(), point01.getX(), point01.getY());
		g2d.setColor(Color.BLACK);
		g2d.setStroke(defaultStroke);
	}

	private void drawYAxis() {
		Stroke defaultStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.GRAY);

		g2d.drawLine(point00.getX(), point00.getY() + ZERO_GAP, point10.getX(), point10.getY());
		g2d.setColor(Color.BLACK);
		g2d.setStroke(defaultStroke);
	}

	//@formatter:off
	private void drawYAxisDescription() {
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		int YAxisDescriptionLength = g2d.getFontMetrics(DESCRIPTION_FONT).stringWidth(chart.getyDescription());
		int ascent = g2d.getFontMetrics(DESCRIPTION_FONT).getAscent();

		g2d.drawString(chart.getyDescription(), - (getHeight()/2 + YAxisDescriptionLength/2), AXIS_BIG_GAP + ascent);
		g2d.setTransform(defaultAt);
	}

	private void drawXAxisDescription() {
		AffineTransform at = new AffineTransform();

		int XAxisDescriptionLength = g2d.getFontMetrics(DESCRIPTION_FONT).stringWidth(chart.getxDescription());

		g2d.drawString(chart.getxDescription(),
				getWidth() / 2 - XAxisDescriptionLength / 2,
				getHeight() - AXIS_BIG_GAP);
	}
	//@formatter:on
}
