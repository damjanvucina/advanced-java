package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	public static final int DEFAULT_STROKE = 2;

	private Graphics2D g2d;

	public GeometricalObjectPainter() {
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		setUpStroke(g2d);
		
		g2d.setColor(line.getFgColor());
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	@Override
	public void visit(Circle circle) {
		setUpStroke(g2d);
		int radius = circle.calculateRadius();
	
		g2d.setColor(circle.getFgColor());
		g2d.drawOval(circle.getStartPoint().x - radius, circle.getStartPoint().y - radius, 2 * radius, 2 * radius);
	}
	
	@Override
	public void visit(FilledCircle filledCircle) {
		setUpStroke(g2d);
		int radius = filledCircle.calculateRadius();
		
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(filledCircle.getStartPoint().x - radius, filledCircle.getStartPoint().y - radius, 2 * radius, 2 * radius);
		
		visit((Circle) filledCircle); 
	}
	
	private void setUpStroke(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(DEFAULT_STROKE));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

}
