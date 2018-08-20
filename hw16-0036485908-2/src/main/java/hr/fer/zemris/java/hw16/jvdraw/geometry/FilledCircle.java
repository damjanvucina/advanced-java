package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class FilledCircle extends GeometricalObject {
	private static final String FILLED_CIRCLE = "Filled circle";

	//@formatter:off
	public FilledCircle(DocumentModel documentModel,
						IColorProvider fgColorProvider,
						IColorProvider bgColorProvider,
						JDrawingCanvas drawingCanvas) {
		
		super(documentModel, fgColorProvider, bgColorProvider, drawingCanvas);
	}
	
	public FilledCircle(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColor(fgColor);
		setBgColor(bgColor);
	}
	//@formatter:on

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	//@formatter:off
	public int calculateRadius() {
		return (int) Math.sqrt(
					 Math.pow((getStartPoint().x - getEndPoint().x), 2) + 
					 Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}
	//@formatter:on

	@Override
	public void paint(Graphics2D g2d) {
		if (startPointSet) {
			GeometricalObjectPainter goPainter = getDrawingCanvas().getGoPainter();
			goPainter.setG2d(g2d);
			goPainter.visit(this);
		}
	}

	//@formatter:off
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new FilledCircle(getStartPoint(), 
								getEndPoint(), 
								getFgColorProvider().getCurrentColor(),
								getBgColorProvider().getCurrentColor());
	}
	//@formatter:on

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Color color = getBgColor();

		sb.append(FILLED_CIRCLE);
		sb.append(" (").append(getStartPoint().x).append(",");
		sb.append(getEndPoint().y).append("), ");
		sb.append(calculateRadius()).append(", ");
		sb.append(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));

		return sb.toString();
	}
}
