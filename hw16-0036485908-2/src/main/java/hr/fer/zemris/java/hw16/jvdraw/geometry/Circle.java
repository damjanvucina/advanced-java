package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Circle extends GeometricalObject {
	private static final String CIRCLE ="Circle";

	public Circle(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, null, drawingCanvas);
	}

	public Circle(Point startPoint, Point endPoint, Color fgColor) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColor(fgColor);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return null;
	}

	public int calculateRadius() {
		return (int) Math.sqrt(Math.pow((getStartPoint().x - getEndPoint().x), 2)
				+ Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}

	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Circle(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (startPointSet) {
			GeometricalObjectPainter goPainter = getDrawingCanvas().getGoPainter();
			goPainter.setG2d(g2d);
			goPainter.visit(this);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(CIRCLE);
		sb.append(" (").append(getStartPoint().x).append(",");
		sb.append(getEndPoint().y).append("), ");
		sb.append(calculateRadius());
		
		return sb.toString();
	}

}
