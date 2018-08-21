package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class FilledCircle extends Circle {
	private static final String FILLED_CIRCLE = "Filled circle";
	
	private Color bgColor;
	private IColorProvider bgColorProvider;

	//@formatter:off
	public FilledCircle(DocumentModel documentModel,
						IColorProvider fgColorProvider,
						IColorProvider bgColorProvider,
						JDrawingCanvas drawingCanvas) {
		
		super(documentModel, fgColorProvider, drawingCanvas);
		this.bgColorProvider = bgColorProvider;
	}
	
	public FilledCircle(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
		super(startPoint, endPoint, fgColor);

		setBgColor(bgColor);
	}
	//@formatter:on
	
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getBgColor() {
		return bgColor != null ? bgColor : bgColorProvider.getCurrentColor();
	}
	
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
		return new FilledCircleEditor(this);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Color color = getBgColor();

		sb.append(FILLED_CIRCLE);
		sb.append(" (").append(getCenter().x).append(",");
		sb.append(getCenter().y).append("), ");
		sb.append(calculateRadius()).append(", ");
		sb.append(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));

		return sb.toString();
	}
}
