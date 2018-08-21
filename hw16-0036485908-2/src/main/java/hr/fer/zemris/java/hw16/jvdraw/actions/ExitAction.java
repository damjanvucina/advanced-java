package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

public class ExitAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private JVDraw window;

	public ExitAction(JVDraw window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<GeometricalObject> currentlyDrawnObjects = window.getDocumentModel().getObjects();
		Path savedPath = window.getImagePath();

		if (UtilityProvider.isImageEdited(savedPath, currentlyDrawnObjects)) {
			//@formatter:off
			int saveResult = JOptionPane.showConfirmDialog(
										 window,
										 "Do you want to save file before closing?",
										 savedPath == null ? "File not saved" : "File edited",
										 JOptionPane.YES_NO_OPTION);
			//@formatter:on
			
			if (saveResult == JOptionPane.YES_OPTION) {
				if (savedPath == null) {
					window.getSaveAsAction().actionPerformed(e);
				} else {
					window.getSaveAction().actionPerformed(e);
				}
			}
		}

		window.dispose();
	}

}
