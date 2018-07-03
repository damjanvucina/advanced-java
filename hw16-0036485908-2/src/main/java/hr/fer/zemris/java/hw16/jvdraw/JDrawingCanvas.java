package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	private static final long serialVersionUID = 1L;

	private DocumentModel documentModel;
	private GeometricalObjectPainter goPainter;
	private JVDraw info;

	public GeometricalObjectPainter getGoPainter() {
		return goPainter;
	}

	public JDrawingCanvas(JVDraw info, DocumentModel documentModel) {
		this.info = info;
		this.documentModel = documentModel;
		goPainter = new GeometricalObjectPainter();

		documentModel.addDrawingModelListener(this);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				info.getCurrentTool().mouseClicked(e);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				info.getCurrentTool().mouseMoved(e);
			}
		});

	}

	public JVDraw getInfo() {
		return info;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		goPainter.setG2d(g2d);
		for (GeometricalObject object : documentModel.getObjects()) {
			object.accept(goPainter);
		}

		info.getCurrentTool().paint(g2d);
	}
}
