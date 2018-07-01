package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;

public class FilledCircle extends GeometricalObject implements Tool{

	private DocumentModel documentModel;
	private IColorProvider fgColorProvider;
	private IColorProvider bgColorProvider;

	public FilledCircle(DocumentModel documentModel, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.documentModel = documentModel;
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
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

}
