package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class ExitApplicationAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	private JNotepadPP window;
	private FormLocalizationProvider flp;

	public ExitApplicationAction(FormLocalizationProvider flp, JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int saveResult;
		DefaultMultipleDocumentModel defaultModel = (DefaultMultipleDocumentModel) model;
		for (int i = 0, length = model.getNumberOfDocuments(); i < length; i++) {

			if (model.getDocument(i).isModified()) {
				defaultModel.setSelectedIndex(i);
				saveResult = JOptionPane.showConfirmDialog(window, flp.getString("doYouWantToSaveFile"),
						flp.getString("doYouWantToSaveFileTitle"), JOptionPane.YES_NO_CANCEL_OPTION);

				if (saveResult == JOptionPane.CANCEL_OPTION) {
					return;
					
				} else if (saveResult == JOptionPane.YES_OPTION) {
					window.getSaveAsDocumentAction().actionPerformed(null);
				}

			}
		}
		window.getStatusPanel().turnOffClock();
		window.dispose();
	}

}
