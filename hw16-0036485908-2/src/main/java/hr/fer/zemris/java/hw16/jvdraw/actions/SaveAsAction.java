package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

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
		int dialogResult = jfc.showSaveDialog(window);

		if (dialogResult == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(window, "Saving cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;

		} else if (dialogResult == JFileChooser.APPROVE_OPTION) {

			int overwriteResult = 0;
			if (Files.exists(jfc.getSelectedFile().toPath())) {
				overwriteResult = JOptionPane.showConfirmDialog(jfc,
						"File already exists. Do you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
				if (overwriteResult == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}

		String jvdRepresentation = UtilityProvider.toJVD(window.getDocumentModel().getObjects());
		byte[] bytes = jvdRepresentation.getBytes(StandardCharsets.UTF_8);
		Path savePath = jfc.getSelectedFile().toPath();

		try {
			Files.write(savePath, bytes);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(window, "File saved successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
	}

}
