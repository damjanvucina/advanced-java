package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

// TODO: Auto-generated Javadoc
/**
 * The Class GeometricalObject.
 */
public abstract class GeometricalObject implements Tool {

	/** The listeners. */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/** The start point. */
	private Point startPoint;
	
	/** The end point. */
	private Point endPoint;
	
	/** The start point set. */
	boolean startPointSet;
	
	/** The document model. */
	private DocumentModel documentModel;
	
	/** The fg color provider. */
	private IColorProvider fgColorProvider;
	
	/** The drawing canvas. */
	private JDrawingCanvas drawingCanvas;
	
	/** The fg color. */
	private Color fgColor;

	/**
	 * Instantiates a new geometrical object.
	 *
	 * @param documentModel the document model
	 * @param fgColorProvider the fg color provider
	 * @param drawingCanvas the drawing canvas
	 */
	//@formatter:off
	public GeometricalObject(DocumentModel documentModel,
							 IColorProvider fgColorProvider,
							 JDrawingCanvas drawingCanvas) {
		
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.drawingCanvas = drawingCanvas;
	}
	//@formatter:off

	/**
	 * Instantiates a new geometrical object.
	 */
	public GeometricalObject() {
	}

	/**
	 * Accept.
	 *
	 * @param v the v
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates the geometrical object editor.
	 *
	 * @return the geometrical object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Clone current object.
	 *
	 * @return the geometrical object
	 */
	public abstract GeometricalObject cloneCurrentObject();

	/**
	 * Sets the fg color.
	 *
	 * @param fgColor the new fg color
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}
	
	/**
	 * Gets the document model.
	 *
	 * @return the document model
	 */
	public DocumentModel getDocumentModel() {
		return documentModel;
	}

	/**
	 * Gets the fg color.
	 *
	 * @return the fg color
	 */
	public Color getFgColor() {
		return fgColor != null ? fgColor : fgColorProvider.getCurrentColor();
	}

	/**
	 * Gets the drawing canvas.
	 *
	 * @return the drawing canvas
	 */
	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

	/**
	 * Gets the fg color provider.
	 *
	 * @return the fg color provider
	 */
	public IColorProvider getFgColorProvider() {
		return fgColorProvider;
	}

	/**
	 * Sets the fg color provider.
	 *
	 * @param fgColorProvider the new fg color provider
	 */
	public void setFgColorProvider(IColorProvider fgColorProvider) {
		this.fgColorProvider = fgColorProvider;
	}
	
	/**
	 * Gets the start point.
	 *
	 * @return the start point
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Checks if is start point set.
	 *
	 * @return true, if is start point set
	 */
	public boolean isStartPointSet() {
		return startPointSet;
	}

	/**
	 * Sets the start point.
	 *
	 * @param startPoint the new start point
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Gets the end point.
	 *
	 * @return the end point
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	
	/**
	 * Calculate points distance.
	 *
	 * @return the int
	 */
	//@formatter:off
	public int calculatePointsDistance() {
		return (int) Math.sqrt(
					 Math.pow((getStartPoint().x - getEndPoint().x), 2) +
				     Math.pow((getStartPoint().y - getEndPoint().y), 2));
	}
	//@formatter:on

	/**
	 * Sets the end point.
	 *
	 * @param endPoint the new end point
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Adds the geometrical object listener.
	 *
	 * @param l the l
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	/**
	 * Removes the geometrical object listener.
	 *
	 * @param l the l
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	/**
	 * Notify listeners.
	 */
	public void notifyListeners() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickedPoint = e.getPoint();

		if (!startPointSet) {
			setStartPoint(clickedPoint);
			startPointSet = true;

		} else {
			if (!getStartPoint().equals(clickedPoint)) {
				setEndPoint(clickedPoint);
				startPointSet = false;
				documentModel.add(cloneCurrentObject());
			}
		}
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (startPointSet) {
			setEndPoint(e.getPoint());
			setFgColor(fgColorProvider.getCurrentColor());
			drawingCanvas.repaint();
		}
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.Tool#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
