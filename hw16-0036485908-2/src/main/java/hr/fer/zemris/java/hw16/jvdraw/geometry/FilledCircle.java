package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class FilledCircle extends GeometricalObject {
	private IColorProvider bgColorProvider;

	//@formatter:off
	public FilledCircle(DocumentModel documentModel,
						IColorProvider fgColorProvider,
						IColorProvider bgColorProvider,
						JDrawingCanvas drawingCanvas) {
		
		super(documentModel, fgColorProvider, drawingCanvas);
		this.bgColorProvider = bgColorProvider;
	}
	
	public FilledCircle(Point startPoint, Point endPoint, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColorProvider(fgColorProvider);
		setBgColorProvider(bgColorProvider);
	}
	
	//@formatter:on

	public IColorProvider getBgColorProvider() {
		return bgColorProvider;
	}

	public void setBgColorProvider(IColorProvider bgColorProvider) {
		this.bgColorProvider = bgColorProvider;
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
	}

	@Override
	public GeometricalObject cloneCurrentObject() {
		return new FilledCircle(getStartPoint(), getEndPoint(), getFgColorProvider(), getBgColorProvider());
	}

}
