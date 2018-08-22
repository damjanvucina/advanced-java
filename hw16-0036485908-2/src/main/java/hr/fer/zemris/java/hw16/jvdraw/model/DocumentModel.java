package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener;
import static hr.fer.zemris.java.hw16.jvdraw.JVDraw.SHIFT_UP;
import static hr.fer.zemris.java.hw16.jvdraw.JVDraw.SHIFT_DOWN;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentModel.
 */
public class DocumentModel implements DrawingModel, GeometricalObjectListener {

	/** The objects. */
	private List<GeometricalObject> objects;
	
	/** The listeners. */
	private List<DrawingModelListener> listeners;

	/**
	 * Instantiates a new document model.
	 */
	public DocumentModel() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public List<GeometricalObject> getObjects() {
		return objects;
	}

	/**
	 * Gets the listeners.
	 *
	 * @return the listeners
	 */
	public List<DrawingModelListener> getListeners() {
		return listeners;
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the new objects
	 */
	public void setObjects(List<GeometricalObject> objects) {
		this.objects = objects;
	}

	/**
	 * Sets the listeners.
	 *
	 * @param listeners the new listeners
	 */
	public void setListeners(List<DrawingModelListener> listeners) {
		this.listeners = listeners;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#getSize()
	 */
	@Override
	public int getSize() {
		return objects.size();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#getObject(int)
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IllegalArgumentException(
					"Valid indices are from 0 to " + (objects.size() - 1) + ", was: " + index);
		}

		return objects.get(index);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#add(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject)
	 */
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

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#remove(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject)
	 */
	@Override
	public void remove(GeometricalObject object) {
		Objects.requireNonNull(object, "Cannot remove null object.");

		int modificationIndex = objects.indexOf(object);
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, modificationIndex, modificationIndex);
		}

		objects.remove(object);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#addDrawingModelListener(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener)
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#removeDrawingModelListener(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener)
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectListener#geometricalObjectChanged(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject)
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		Objects.requireNonNull(o, "Geometrical object cannot be null");

		int modificationIndex = objects.indexOf(o);
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, modificationIndex, modificationIndex);
		}
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel#changeOrder(hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject, int)
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		if (offset != SHIFT_UP && offset != SHIFT_DOWN) {
			throw new IllegalArgumentException("Shifting offset must be 1 or -1, was: " + offset);
		}

		int oldIndex = objects.indexOf(object);
		int newIndex = oldIndex + offset;
		if (newIndex >= 0 && newIndex < objects.size()) {
			Collections.swap(objects, oldIndex, newIndex);

			for (DrawingModelListener listener : listeners) {
				listener.objectsChanged(this, Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex));
			}
		}
	}
}
