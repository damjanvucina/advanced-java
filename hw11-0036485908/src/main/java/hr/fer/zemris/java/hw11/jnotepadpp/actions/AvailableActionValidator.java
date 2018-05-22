package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class AvailableActionValidator extends AbstractAction {
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public AvailableActionValidator(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		validateActions();

	}

	private void validateActions() {
		if (model.getNumberOfDocuments() > 0) {

			window.getSaveAsDocumentAction().setEnabled(true);
			window.getCloseTabAction().setEnabled(true);
			window.getShowStatsAction().setEnabled(true);

		} else {
			window.getSaveDocumentAction().setEnabled(false);
			window.getSaveAsDocumentAction().setEnabled(false);
			window.getCloseTabAction().setEnabled(false);
			window.getShowStatsAction().setEnabled(false);
		}

		if (model.getCurrentDocument() != null && model.getCurrentDocument().getFilePath() != null) {
			window.getSaveDocumentAction().setEnabled(true);
		} else {
			window.getSaveDocumentAction().setEnabled(false);
		}
	}
}
