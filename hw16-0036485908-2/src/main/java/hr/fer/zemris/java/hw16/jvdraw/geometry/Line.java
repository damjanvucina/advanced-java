package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Line extends GeometricalObject implements Tool {
	
	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;
	private JDrawingCanvas drawingCanvas;

	public Line(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.drawingCanvas = drawingCanvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickedPoint = e.getPoint();

		if (!startPointSet) {
			setStartPoint(clickedPoint);
			startPointSet = true;
			
		} else {
			setEndPoint(clickedPoint);
			startPointSet = false;
			documentModel.add(this);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(startPointSet) {
			setEndPoint(e.getPoint());
			drawingCanvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(startPointSet) {
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawLine(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
		}
	}

	public IColorProvider getFgColorProvider() {
		return fgColorProvider;
	}

	public void setFgColorProvider(IColorProvider fgColorProvider) {
		this.fgColorProvider = fgColorProvider;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return null;
	}

}
