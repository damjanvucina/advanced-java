package hr.fer.zemris.java.hw16.jvdraw.geometry;

public abstract class GeometricalObject implements GeometricalObjectListener{

	public abstract void accept(GeometricalObjectVisitor v);
	
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
	}
}
