package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

public interface DrawingModel {
	int getSize();
	GeometricalObject getObject(int index);
	void add(GeometricalObject object);
	void addDrawingModelListener(DrawingModelListener l);
	void removeDrawingModelListener(DrawingModelListener l);
}
