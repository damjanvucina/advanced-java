package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Circle extends GeometricalObject implements Tool {
	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;
	private JDrawingCanvas drawingCanvas;

	public Circle(DocumentModel documentModel, IColorProvider fgColorProvider, JDrawingCanvas drawingCanvas) {
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.drawingCanvas = drawingCanvas;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return null;
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
		if (startPointSet) {
			setEndPoint(e.getPoint());
			drawingCanvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
	}

}
