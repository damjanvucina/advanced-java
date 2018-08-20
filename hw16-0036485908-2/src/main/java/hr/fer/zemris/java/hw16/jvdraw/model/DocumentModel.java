package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener;

public class DocumentModel implements DrawingModel, GeometricalObjectListener {

	private List<GeometricalObject> objects;
	private List<DrawingModelListener> listeners;

	public List<GeometricalObject> getObjects() {
		return objects;
	}

	public List<DrawingModelListener> getListeners() {
		return listeners;
	}

	public void setObjects(List<GeometricalObject> objects) {
		this.objects = objects;
	}

	public void setListeners(List<DrawingModelListener> listeners) {
		this.listeners = listeners;
	}

	public DocumentModel() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IllegalArgumentException(
					"Valid indices are from 0 to " + (objects.size() - 1) + ", was: " + index);
		}

		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot add null object");

		objects.add(object);
		object.addGeometricalObjectListener(this);

		int modificationIndex = objects.size() - 1;
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, modificationIndex, modificationIndex);
		}

	}
	
	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot remove null object.");

		int modificationIndex = objects.indexOf(object);
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, modificationIndex, modificationIndex);
		}
		
		objects.remove(object);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		Objects.requireNonNull(o, "Geometrical object cannot be null");
		
		int modificationIndex = objects.indexOf(o);
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, modificationIndex, modificationIndex);
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if (offset <= 0) {
			return;
		}

		objects.add(offset, object);
	}
}
