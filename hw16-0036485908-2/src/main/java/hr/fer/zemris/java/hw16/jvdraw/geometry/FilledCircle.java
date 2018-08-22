package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

// TODO: Auto-generated Javadoc
/**
 * The Class FilledCircle.
 */
public class FilledCircle extends Circle {
	
	/** The Constant FILLED_CIRCLE. */
	private static final String FILLED_CIRCLE = "Filled circle";
	
	/** The bg color. */
	private Color bgColor;
	
	/** The bg color provider. */
	private IColorProvider bgColorProvider;

	/**
	 * Instantiates a new filled circle.
	 *
	 * @param documentModel the document model
	 * @param fgColorProvider the fg color provider
	 * @param bgColorProvider the bg color provider
	 * @param drawingCanvas the drawing canvas
	 */
	//@formatter:off
	public FilledCircle(DocumentModel documentModel,
						IColorProvider fgColorProvider,
						IColorProvider bgColorProvider,
						JDrawingCanvas drawingCanvas) {
		
		super(documentModel, fgColorProvider, drawingCanvas);
		this.bgColorProvider = bgColorProvider;
	}
	
	/**
	 * Instantiates a new filled circle.
	 *
	 * @param startPoint the start point
	 * @param endPoint the end point
	 * @param fgColor the fg color
	 * @param bgColor the bg color
	 */
	public FilledCircle(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
		super(startPoint, endPoint, fgColor);

		setBgColor(bgColor);
	}
	//@formatter:on
	
	/**
	 * Sets the bg color.
	 *
	 * @param bgColor the new bg color
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public Color getBgColor() {
		return bgColor != null ? bgColor : bgColorProvider.getCurrentColor();
	}
	
	/**
	 * Gets the bg color provider.
	 *
	 * @return the bg color provider
	 */
	public IColorProvider getBgColorProvider() {
		return bgColorProvider;
	}

	/**
	 * Sets the bg color provider.
	 *
	 * @param bgColorProvider the new bg color provider
	 */
	public void setBgColorProvider(IColorProvider bgColorProvider) {
		this.bgColorProvider = bgColorProvider;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.Circle#accept(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectVisitor)
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.Circle#createGeometricalObjectEditor()
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.Circle#paint(java.awt.Graphics2D)
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
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.Circle#cloneCurrentObject()
	 */
	//@formatter:off
	@Override
	public GeometricalObject cloneCurrentObject() {
		return new FilledCircle(getStartPoint(), 
								getEndPoint(), 
								getFgColorProvider().getCurrentColor(),
								getBgColorProvider().getCurrentColor());
	}
	//@formatter:on

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.Circle#toString()
	 */
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
