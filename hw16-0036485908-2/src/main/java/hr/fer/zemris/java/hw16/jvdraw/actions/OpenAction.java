package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenAction.
 */
public class OpenAction extends AbstractAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private JVDraw window;

	/**
	 * Instantiates a new open action.
	 *
	 * @param window the window
	 */
	public OpenAction(JVDraw window) {
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
		if (!Files.isReadable(filePath) || UtilityProvider.isInvalidExtension(filePath, Arrays.asList(UtilityProvider.getJvdExtension()))) {
			JOptionPane.showMessageDialog(window, fileName.getAbsolutePath() +
					" is not readable. Supported extensions: .jvd", "File not readable",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		List<String> lines = UtilityProvider.loadFile(filePath);
		List<GeometricalObject> loadedObjects = UtilityProvider.fromFile(lines);
		
		if(loadedObjects == null) {
			JOptionPane.showMessageDialog(window,
										  "Requested file name is not valid. Supported file extension: .jvd",
										  "Invalid file name",
										  JOptionPane.WARNING_MESSAGE);
			return;
		}
		//@formatter:on
		
		window.getDocumentModel().getObjects().clear();
		window.setImagePath(filePath);
		for(GeometricalObject object : loadedObjects) {
			window.getDocumentModel().add(object);
		}
	}

}
