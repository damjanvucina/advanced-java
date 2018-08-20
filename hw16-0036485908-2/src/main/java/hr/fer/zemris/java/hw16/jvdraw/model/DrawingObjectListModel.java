package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

//@formatter:off
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
									implements DrawingModelListener {
//@formatter:on
	private static final long serialVersionUID = 1L;
	
	private DrawingModel drawingModel;
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		
		drawingModel.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.add(l);
	}
	
	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.remove(l);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		
		notifyListeners(event, l -> l.intervalAdded(event));
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		
		notifyListeners(event, l -> l.intervalRemoved(event));
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		
		notifyListeners(event, l -> l.contentsChanged(event));
	}

	private void notifyListeners(ListDataEvent event, Consumer<ListDataListener> action) {
		for (ListDataListener l : listeners) {
			action.accept(l);
		}
	}

}
