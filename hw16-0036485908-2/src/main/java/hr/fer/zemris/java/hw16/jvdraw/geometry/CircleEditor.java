package hr.fer.zemris.java.hw16.jvdraw.geometry;

public class CircleEditor extends AbstractCircleEditor {
	private static final long serialVersionUID = 1L;

	public CircleEditor(Circle circle) {
		super(circle);

		initialize();
		setUp();
	}
}
