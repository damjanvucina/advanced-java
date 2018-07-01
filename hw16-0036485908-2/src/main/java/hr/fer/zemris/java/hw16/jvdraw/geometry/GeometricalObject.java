package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GeometricalObject {
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	private Point startPoint;
	private Point endPoint;
	boolean startPointSet;

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		startPointSet = true;
		
		notifyListeners();
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		startPointSet = false;
		
		notifyListeners();
	}

	public abstract void accept(GeometricalObjectVisitor v);

	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

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
}
