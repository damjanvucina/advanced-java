package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private static final int AXIS_SMALL_GAP = 10;
	private static final int AXIS_BIG_GAP = 20;
	private static final int ZERO_GAP = 5;
	private static final Font DESCRIPTION_FONT = new Font("Arial", Font.PLAIN, 18);
	private static final Font COORDINATES_FONT = new Font("Arial", Font.BOLD, 15);
	private static final Color GRID_COLOR = Color.decode("#f4b8a1");

	private BarChart chart;
	private Graphics2D g2d;

	private XYValue point00;
	private XYValue point01;
	private XYValue point10;
	@SuppressWarnings("unused")
	private XYValue point11;

	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g2d = (Graphics2D) g;
		g2d.setFont(DESCRIPTION_FONT);

		intializeGraphEndPoints();

		drawVerticalGridBarsAndXCoordinates();
		drawHorizontalGrid();

		drawYAxis();
		drawXAxis();
		drawAxesArrows();

		drawXAxisDescription();
		drawYAxisDescription();
	}
	
	//@formatter:off
	private void drawAxesArrows() {
		
		g2d.setColor(Color.GRAY);
		g2d.fillPolygon(new Polygon(
						new int[] { point01.getX(), point01.getX(), point01.getX() + ZERO_GAP },
						new int[] { point01.getY() + ZERO_GAP, point01.getY() - ZERO_GAP, point01.getY()}, 3));
		
		g2d.fillPolygon(new Polygon(
				new int[] { point10.getX() - ZERO_GAP, point10.getX() + ZERO_GAP, point10.getX()},
				new int[] { point10.getY(), point10.getY(), point10.getY() - ZERO_GAP}, 3));
		
		g2d.setColor(Color.BLACK);
	}
	//@formatter:on

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

	private void drawHorizontalGrid() {
		int numOfSpaces = (chart.getMaxY() - chart.getMinY()) / chart.getGapY();
		int barLength = (int) Math.hypot(point00.getX() - point10.getX(), point00.getY() - point10.getY())
				/ numOfSpaces;

		g2d.setColor(GRID_COLOR);
		int yCoordinate = chart.getMinY();
		for (int i = 1; i <= numOfSpaces; i++) {
			g2d.drawLine(point00.getX() - ZERO_GAP, point00.getY() - i * barLength + ZERO_GAP, point01.getX(),
					point01.getY() - i * barLength + ZERO_GAP);

			drawYCoordinate(point00.getX(), point00.getY() - (i - 1) * barLength,
					String.valueOf(yCoordinate + (i - 1) * chart.getGapY()));

		}

		drawYCoordinate(point00.getX(), point00.getY() - (numOfSpaces) * barLength,
				String.valueOf(yCoordinate + (numOfSpaces) * chart.getGapY()));

		g2d.setColor(Color.BLACK);
	}

	private void drawYCoordinate(int x, int y, String yCoordinate) {
		int textlength = g2d.getFontMetrics().stringWidth(yCoordinate);
		int textAscent = g2d.getFontMetrics().getAscent();

		g2d.setFont(COORDINATES_FONT);
		g2d.setColor(Color.black);

		g2d.drawString(yCoordinate, x - textlength - ZERO_GAP, y + textAscent / 2);

		g2d.setColor(GRID_COLOR);
		g2d.setFont(DESCRIPTION_FONT);
	}

	private void drawVerticalGridBarsAndXCoordinates() {
		int numOfBars = chart.getValues().size();
		int barLength = (int) Math.hypot(point00.getX() - point01.getX(), point00.getY() - point01.getY()) / numOfBars;
		XYValue bar00 = point00;

		g2d.setColor(GRID_COLOR);
		for (int i = 1; i <= numOfBars; i++) {

			bar00 = drawBar(bar00, new XYValue(point00.getX() + i * barLength, point00.getY() + 0),
					chart.getValues().get(i - 1).getY(), chart.getValues().get(i - 1).getX());

			g2d.drawLine(point00.getX() + i * barLength, point00.getY() + ZERO_GAP, point10.getX() + i * barLength,
					point10.getY());

		}
		g2d.setColor(Color.BLACK);
	}

	private XYValue drawBar(XYValue bar00, XYValue bar01, int value, int xCoordinate) {
		int numOfSpaces = (chart.getMaxY() - chart.getMinY()) / chart.getGapY();
		int barLength = (int) Math.hypot(point00.getX() - point10.getX(), point00.getY() - point10.getY())
				/ numOfSpaces;

		XYValue bar10 = new XYValue(bar00.getX(), bar00.getY() - value * barLength);
		XYValue bar11 = new XYValue(bar01.getX(), bar01.getY() - value * barLength);

		int width = calculateXYDistance(bar00, bar01);
		int height = calculateXYDistance(bar00, bar10);

		g2d.fillRect(bar00.getX(), bar00.getY() - height / 2 + ZERO_GAP, width, height / 2 - ZERO_GAP);
		drawBarSeparator(bar01, bar11);
		g2d.setColor(Color.BLACK);
		drawXCoordinate(bar00, bar01, String.valueOf(xCoordinate));
		g2d.setColor(GRID_COLOR);

		return bar01;
	}

	private void drawXCoordinate(XYValue bar00, XYValue bar01, String xCoordinate) {
		int textlength = g2d.getFontMetrics().stringWidth(xCoordinate);
		int baseLineX = bar00.getX() + calculateXYDistance(bar00, bar01) / 2 - textlength / 2;
		int baseLineY = bar00.getY() + AXIS_SMALL_GAP + ZERO_GAP;

		g2d.setFont(COORDINATES_FONT);
		g2d.drawString(xCoordinate, baseLineX, baseLineY);
		g2d.setFont(DESCRIPTION_FONT);
	}

	private void drawBarSeparator(XYValue bar01, XYValue bar11) {
		Stroke defaultStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);

		g2d.drawLine(bar01.getX(), bar01.getY(), bar11.getX(), bar11.getY());

		g2d.setColor(GRID_COLOR);
		g2d.setStroke(defaultStroke);
	}

	private int calculateXYDistance(XYValue bar00, XYValue bar01) {
		return (int) Math.hypot(bar00.getX() - bar01.getX(), bar00.getY() - bar01.getY());
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

		g2d.drawString(chart.getyDescription(), - ((calculateXYDistance(point00, point10))/2 + YAxisDescriptionLength), AXIS_SMALL_GAP + ascent);
		
		g2d.setTransform(defaultAt);
	}

	private void drawXAxisDescription() {
		int XAxisDescriptionLength = g2d.getFontMetrics(DESCRIPTION_FONT).stringWidth(chart.getxDescription());
		
		g2d.drawString(chart.getxDescription(),
				point01.getX() - (point01.getX() - point00.getX())/2 - XAxisDescriptionLength / 2,
				getHeight() - AXIS_BIG_GAP);
	}
	//@formatter:on
}
