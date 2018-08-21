package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Line extends GeometricalObject {
	private static final String LINE = "Line";

	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}

	public Line(Point startPoint, Point endPoint, Color fgColor) {
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
		return new LineEditor(this);
	}

	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Line(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
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
		
		sb.append(LINE);
		sb.append(" (").append(getStartPoint().x).append(",");
		sb.append(getStartPoint().y).append(")-");
		
		sb.append("(").append(getEndPoint().x).append(",");
		sb.append(getEndPoint().y).append(")");
		
		return sb.toString();
	}
}
