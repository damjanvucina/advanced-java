package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

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
		//@formatter:on
	}

}
