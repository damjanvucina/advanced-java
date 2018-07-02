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

public abstract class GeometricalObject implements Tool {

	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	private Point startPoint;
	private Point endPoint;
	boolean startPointSet;
	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;
	private IColorProvider bgColorProvider;
	private JDrawingCanvas drawingCanvas;
	private Color fgColor;
	private Color bgColor;

	//@formatter:off
	public GeometricalObject(DocumentModel documentModel,
							 IColorProvider fgColorProvider,
							 IColorProvider bgColorProvider,
							 JDrawingCanvas drawingCanvas) {
		
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		this.drawingCanvas = drawingCanvas;
	}
	//@formatter:off

	public GeometricalObject() {
	}

	public abstract void accept(GeometricalObjectVisitor v);

	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	public abstract GeometricalObject cloneCurrentObject();

	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}

	public Color getFgColor() {
		return fgColor != null ? fgColor : fgColorProvider.getCurrentColor();
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getBgColor() {
		return bgColor != null ? bgColor : bgColorProvider.getCurrentColor();
	}

	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

	public IColorProvider getFgColorProvider() {
		return fgColorProvider;
	}

	public void setFgColorProvider(IColorProvider fgColorProvider) {
		this.fgColorProvider = fgColorProvider;
	}
	
	public IColorProvider getBgColorProvider() {
		return bgColorProvider;
	}

	public void setBgColorProvider(IColorProvider bgColorProvider) {
		this.bgColorProvider = bgColorProvider;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public boolean isStartPointSet() {
		return startPointSet;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	public void notifyListeners() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickedPoint = e.getPoint();

		if (!startPointSet) {
			setStartPoint(clickedPoint);
			startPointSet = true;

		} else {
			setEndPoint(clickedPoint);
			startPointSet = false;
			documentModel.add(cloneCurrentObject());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (startPointSet) {
			setEndPoint(e.getPoint());
			setFgColor(fgColorProvider.getCurrentColor());
			drawingCanvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
