package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

// TODO: Auto-generated Javadoc
/**
 * The Class Line.
 */
public class Line extends GeometricalObject {
	
	/** The Constant LINE. */
	private static final String LINE = "Line";

	/**
	 * Instantiates a new line.
	 *
	 * @param documentModel the document model
	 * @param fgColorProvider the fg color provider
	 * @param drawingCanvas the drawing canvas
	 */
	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		super(documentModel, fgColorProvider, drawingCanvas);
	}

	/**
	 * Instantiates a new line.
	 *
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @param fgColor the fg color
	 */
	public Line(Point startPoint, Point endPoint, Color fgColor) {
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
		return new LineEditor(this);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject#cloneCurrentObject()
	 */
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new Line(getStartPoint(), getEndPoint(), getFgColorProvider().getCurrentColor());
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
