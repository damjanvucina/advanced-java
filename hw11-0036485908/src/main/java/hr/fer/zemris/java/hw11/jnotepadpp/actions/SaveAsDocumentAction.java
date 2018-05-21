package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class SaveAsDocumentAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public SaveAsDocumentAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		int dialogResult = jfc.showSaveDialog(window);
		
		if (dialogResult == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(window, "Saving canceled", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;

		} else if (dialogResult == JFileChooser.APPROVE_OPTION) {
			
			int overwriteResult = 0;
			if (Files.exists(jfc.getSelectedFile().toPath())) {
				overwriteResult = JOptionPane.showConfirmDialog(jfc, "File already exists. Do you want to overwrite it?",
						"File exists", JOptionPane.YES_NO_OPTION);
				if (overwriteResult == JOptionPane.NO_OPTION) {
					return;
				}
			}
		}

		Path savePath = jfc.getSelectedFile().toPath();

		int indexOfSelectedTab = ((DefaultMultipleDocumentModel) model).getSelectedIndex();

		model.saveDocument(model.getDocument(indexOfSelectedTab), savePath);

		JTextArea tab = (JTextArea) ((JScrollPane) ((DefaultMultipleDocumentModel) model).getSelectedComponent())
				.getViewport().getView();
		tab.setToolTipText(String.valueOf(model.getCurrentDocument().getFilePath()));
		String title = (String.valueOf(model.getCurrentDocument().getFilePath().getFileName()));
		((DefaultMultipleDocumentModel) model).setTitleAt(indexOfSelectedTab, title);
		
		window.getAvailableActionValidator().actionPerformed(e);
	}

}
