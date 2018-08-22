package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

// TODO: Auto-generated Javadoc
/**
 * The Class DrawingObjectListModel.
 */
//@formatter:off
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
									implements DrawingModelListener {

/** The Constant serialVersionUID. */
//@formatter:on
	private static final long serialVersionUID = 1L;
	
	/** The drawing model. */
	private DrawingModel drawingModel;
	
	/** The listeners. */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * Instantiates a new drawing object list model.
	 *
	 * @param drawingModel the drawing model
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		
		drawingModel.addDrawingModelListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.add(l);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.remove(l);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsAdded(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		
		notifyListeners(event, l -> l.intervalAdded(event));
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsRemoved(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		
		notifyListeners(event, l -> l.intervalRemoved(event));
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsChanged(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		
		notifyListeners(event, l -> l.contentsChanged(event));
	}

	/**
	 * Notify listeners.
	 *
	 * @param event the event
	 * @param action the action
	 */
	private void notifyListeners(ListDataEvent event, Consumer<ListDataListener> action) {
		for (ListDataListener l : listeners) {
			action.accept(l);
		}
	}

}
