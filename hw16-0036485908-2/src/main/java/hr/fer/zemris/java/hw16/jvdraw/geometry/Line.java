package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.Tool;

public class Line extends GeometricalObject implements Tool {

	@SuppressWarnings("unused")
	private Point startPoint;
	@SuppressWarnings("unused")
	private Point endPoint;

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point screenPoint = e.getPoint();
		if (e.getClickCount() % 2 == 1) {
			startPoint = screenPoint;
		} else {
			endPoint = screenPoint;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return null;
	}

}
