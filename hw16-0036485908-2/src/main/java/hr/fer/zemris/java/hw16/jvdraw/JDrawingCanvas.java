package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	private static final long serialVersionUID = 1L;

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
	}

}
