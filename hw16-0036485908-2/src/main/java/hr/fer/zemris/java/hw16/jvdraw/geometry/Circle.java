package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

// TODO: Auto-generated Javadoc
/**
 * The Class Circle.
 */
public class Circle extends GeometricalObject {
	
	/** The Constant CIRCLE. */
	private static final String CIRCLE ="Circle";

	/**
	 * Instantiates a new circle.
	 *
	 * @param documentModel the document model
	 * @param fgColorProvider the fg color provider
	 * @param drawingCanvas the drawing canvas
	 */
	public Circle(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}

	/**
	 * Instantiates a new circle.
	 *
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @param fgColor the fg color
	 */
	public Circle(Point startPoint, Point endPoint, Color fgColor) {
		setStartPoint(startPoint);
		setEndPoint(endPoint);
		setFgColor(fgColor);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject#accept(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor)
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject#createGeometricalObjectEditor()
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * Calculate radius.
	 *
	 * @return the int
	 */
	//@formatter:off
	public int calculateRadius() {
		return (int) Math.sqrt(
					 Math.pow((getStartPoint().x - getEndPoint().x), 2) +
					 Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}
	//@formatter:on

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject#cloneCurrentObject()
	 */
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Circle(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#paint(java.awt.Graphics2D)
	 */
	@Override
	public void paint(Graphics2D g2d) {
		if (startPointSet) {
			GeometricalObjectPainter goPainter = getDrawingCanvas().getGoPainter();
			goPainter.setG2d(g2d);
			goPainter.visit(this);
		}
	}
	
	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public Point getCenter() {
		return getStartPoint();
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(CIRCLE);
		sb.append(" (").append(getCenter().x).append(",");
		sb.append(getCenter().y).append("), ");
		sb.append(calculateRadius());
		
		return sb.toString();
	}

}
