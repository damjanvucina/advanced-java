package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import static hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP.DEFAULT_TITLE;
import static hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP.UNTITLED;
import static hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP.TITLE_SEPARATOR;

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

		validateWindowTitle();
	}

	private void validateWindowTitle() {
		if (model.getNumberOfDocuments() == 0) {
			window.setTitle(DEFAULT_TITLE);

		} else if (model.getCurrentDocument().getFilePath() == null) {
			window.setTitle(UNTITLED + TITLE_SEPARATOR + DEFAULT_TITLE);

		} else {
			window.setTitle(model.getCurrentDocument().getFilePath().getFileName() + TITLE_SEPARATOR + DEFAULT_TITLE);
		}
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
