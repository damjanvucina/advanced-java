package hr.fer.zemris.java.hw16.jvdraw.geometry;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving geometricalObject events.
 * The class that is interested in processing a geometricalObject
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGeometricalObjectListener<code> method. When
 * the geometricalObject event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GeometricalObjectEvent
 */
public interface GeometricalObjectListener {
	
	/**
	 * Geometrical object changed.
	 *
	 * @param o the o
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
