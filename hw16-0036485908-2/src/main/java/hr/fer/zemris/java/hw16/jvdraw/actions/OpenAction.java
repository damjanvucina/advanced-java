package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

public class OpenAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private JVDraw window;

	public OpenAction(JVDraw window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open document");
		jfc.setFileFilter(UtilityProvider.getJvdFilter());

		int dialogResult = jfc.showOpenDialog(window);
		if (dialogResult != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = jfc.getSelectedFile();
		Path filePath = fileName.toPath();

		//@formatter:off
		if (!Files.isReadable(filePath) || UtilityProvider.isInvalidPath(filePath)) {
			JOptionPane.showMessageDialog(window, fileName.getAbsolutePath() +
					" is not readable. Supported extensions: .jvd", "File not readable",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		List<String> jvdLines = null;
		try {
			jvdLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<GeometricalObject> loadedObjects = UtilityProvider.fromJVD(jvdLines);
		if(loadedObjects == null) {
			JOptionPane.showMessageDialog(window,
										  "Requested file name is not valid. Supported file extension: .jvd",
										  "Invalid file name",
										  JOptionPane.WARNING_MESSAGE);
			return;
		}
		//@formatter:on
		
		window.getDocumentModel().getObjects().clear();
		for(GeometricalObject object : loadedObjects) {
			window.getDocumentModel().add(object);
		}
	}

}
