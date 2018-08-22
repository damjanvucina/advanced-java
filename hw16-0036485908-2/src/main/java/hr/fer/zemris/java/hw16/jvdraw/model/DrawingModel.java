package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface DrawingModel.
 */
public interface DrawingModel {

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	int getSize();

	/**
	 * Gets the object.
	 *
	 * @param index the index
	 * @return the object
	 */
	GeometricalObject getObject(int index);

	/**
	 * Adds the.
	 *
	 * @param object the object
	 */
	void add(GeometricalObject object);

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l the l
	 */
	void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the drawing model listener.
	 *
	 * @param l the l
	 */
	void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the.
	 *
	 * @param object the object
	 */
	void remove(GeometricalObject object);

	/**
	 * Change order.
	 *
	 * @param object the object
	 * @param offset the offset
	 */
	void changeOrder(GeometricalObject object, int offset);
}
