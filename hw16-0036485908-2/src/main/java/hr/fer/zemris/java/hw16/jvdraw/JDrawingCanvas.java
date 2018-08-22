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

/**
 * The Class JDrawingCanvas.
 * 
 * @author Damjan Vučina
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	
	/** The Constant CANVAS_COLOR. */
	public static final Color CANVAS_COLOR = Color.WHITE;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The document model. */
	private DocumentModel documentModel;
	
	/** The go painter. */
	private GeometricalObjectPainter goPainter;
	
	/** The info. */
	private JVDraw info;

	/**
	 * Gets the go painter.
	 *
	 * @return the go painter
	 */
	public GeometricalObjectPainter getGoPainter() {
		return goPainter;
	}

	/**
	 * Instantiates a new j drawing canvas.
	 *
	 * @param info the info
	 * @param documentModel the document model
	 */
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

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public JVDraw getInfo() {
		return info;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsAdded(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsRemoved(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener#objectsChanged(hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel, int, int)
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(CANVAS_COLOR);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		goPainter.setG2d(g2d);
		for (GeometricalObject object : documentModel.getObjects()) {
			object.accept(goPainter);
		}

		info.getCurrentTool().paint(g2d);
	}
}
