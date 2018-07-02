package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class FilledCircle extends GeometricalObject {
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
		return null;
	}

	public int calculateRadius() {
		return (int) Math.sqrt(Math.pow((getStartPoint().x - getEndPoint().x), 2)
				+ Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}

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

}
