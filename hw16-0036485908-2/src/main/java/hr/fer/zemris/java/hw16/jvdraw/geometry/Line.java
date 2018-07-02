package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Line extends GeometricalObject {

	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}
	
	public Line(Point startPoint, Point endPoint, IColorProvider fgColorProvider) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColorProvider(fgColorProvider);
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
	public void paint(Graphics2D g2d) {
		if (startPointSet) {
			g2d.setColor(getFgColorProvider().getCurrentColor());
			g2d.drawLine(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
		}
	}

	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Line(getStartPoint(), getEndPoint(), getFgColorProvider());
	}
}
