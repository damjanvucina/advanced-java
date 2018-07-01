package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class Line extends GeometricalObject implements Tool {

	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;

	public Line(DocumentModel documentModel, IColorProvider fgColorProvider) {
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
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
		} else {
			setEndPoint(clickedPoint);
			documentModel.add(this);
		}
		
		notifyListeners();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setEndPoint(e.getPoint());
		
		notifyListeners();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawLine(getStartPoint().x, getStartPoint().y, getEndPoint().x, getEndPoint().y);
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
