package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import static hr.fer.zemris.java.hw16.jvdraw.actions.UtilityProvider.JVD_EXTENSION;

public class SaveAsAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private JVDraw window;
	
	public SaveAsAction(JVDraw window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		jfc.setFileFilter(UtilityProvider.getJvdFilter());
		int dialogResult = jfc.showSaveDialog(window);

		if (dialogResult == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(window, "Saving cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;

		} 
		
		Path savePath = jfc.getSelectedFile().toPath();
		if(UtilityProvider.isInvalidExtension(savePath, Arrays.asList(UtilityProvider.getJvdExtension()))) {
			JOptionPane.showMessageDialog(window, "Requested file name is not valid. Supported file extension: .jvd",
												  "Invalid file name", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(UtilityProvider.extensionNotSet(savePath)) {
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

		UtilityProvider.saveJVD(savePath, window.getDocumentModel().getObjects());
		window.setImagePath(savePath);
		
		JOptionPane.showMessageDialog(window, "File saved successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
	}





}
