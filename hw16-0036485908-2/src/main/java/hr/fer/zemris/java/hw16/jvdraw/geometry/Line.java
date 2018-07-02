package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Line extends GeometricalObject {

	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, null, drawingCanvas);
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
		return null;
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
}
