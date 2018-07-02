package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Circle extends GeometricalObject {
	
	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;
	private JDrawingCanvas drawingCanvas;

	public Circle(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}
	
	public Circle(Point startPoint, Point endPoint, IColorProvider fgColorProvider) {
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
	}

	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Circle(getStartPoint(), getEndPoint(), getFgColorProvider());
	}

}
