package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

public class SaveAsAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static final String JVD_EXTENSION = ".jvd";

	private JVDraw window;
	private FileNameExtensionFilter jvdFilter;

	public SaveAsAction(JVDraw window) {
		this.window = window;
		
		jvdFilter = new FileNameExtensionFilter(".jvd", "jvd");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		jfc.setFileFilter(jvdFilter);
		int dialogResult = jfc.showSaveDialog(window);

		if (dialogResult == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(window, "Saving cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;

		} 
		
		Path savePath = jfc.getSelectedFile().toPath();
		if(isInvalidPath(savePath)) {
			JOptionPane.showMessageDialog(window, "Requested file name is not valid. Supported file extension: .jvd",
												  "Invalid file name", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(extensionNotSet(savePath)) {
			savePath = Paths.get(String.valueOf(savePath) + JVD_EXTENSION);
		}
		
		if (dialogResult == JFileChooser.APPROVE_OPTION) {
			int overwriteResult = 0;
			if (Files.exists(savePath)) {
				overwriteResult = JOptionPane.showConfirmDialog(jfc,
						"File already exists. Do you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
				if (overwriteResult == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}

		String jvdRepresentation = UtilityProvider.toJVD(window.getDocumentModel().getObjects());
		byte[] bytes = jvdRepresentation.getBytes(StandardCharsets.UTF_8);
		UtilityProvider.saveJVD(savePath, bytes);
		
		JOptionPane.showMessageDialog(window, "File saved successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean extensionNotSet(Path savePath) {
		return !String.valueOf(savePath).contains(".");
	}

	//@formatter:off
	private boolean isInvalidPath(Path path) {
		String p = String.valueOf(path);
		int numOfDots = p.length() - p.replace(".", "").length();
		
		return numOfDots > 1 || (numOfDots == 1 && !p.endsWith(JVD_EXTENSION));
	}
	//@formatter:on

}
