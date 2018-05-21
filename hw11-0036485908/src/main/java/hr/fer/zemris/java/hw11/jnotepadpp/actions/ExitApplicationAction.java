package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class ExitApplicationAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public ExitApplicationAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int saveResult;
		DefaultMultipleDocumentModel defaultModel = (DefaultMultipleDocumentModel) model;
		for (int i = 0, length = model.getNumberOfDocuments(); i < length; i++) {

			if (model.getDocument(i).isModified()) {
				defaultModel.setSelectedIndex(i);
				saveResult = JOptionPane.showConfirmDialog(window, "Do you want to save file before closing?",
						"File not saved", JOptionPane.YES_NO_OPTION);

				if (saveResult == JOptionPane.YES_OPTION) {
					window.getSaveAsDocumentAction().actionPerformed(null);
				}

			}
		}

		System.exit(0);
	}

}
