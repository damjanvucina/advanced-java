package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class CloseTabAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	private JNotepadPP window;
	private FormLocalizationProvider flp;

	public CloseTabAction(FormLocalizationProvider flp, JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int indexOfSelectedTab = ((DefaultMultipleDocumentModel) model).getSelectedIndex();

		int saveResult;
		if (model.getDocument(indexOfSelectedTab).isModified()) {
			saveResult = JOptionPane.showConfirmDialog(window, flp.getString("doYouWantToSaveFile"),
					flp.getString("doYouWantToSaveFileTitle"), JOptionPane.YES_NO_OPTION);

			if (saveResult == JOptionPane.YES_OPTION) {
				window.getSaveAsDocumentAction().actionPerformed(null);
			}
		}
		
		model.getDocument(indexOfSelectedTab);
		model.closeDocument(model.getDocument(indexOfSelectedTab));

		window.getAvailableActionValidator().actionPerformed(null);
		
	}
}
