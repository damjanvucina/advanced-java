package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class CutTextAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	private JNotepadPP window;
	private FormLocalizationProvider flp;

	public CutTextAction(FormLocalizationProvider flp, JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(model.getNumberOfDocuments() == 0) {
			JOptionPane.showMessageDialog(window, flp.getString("docNotFound"), flp.getString("cutError"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		window.getCopyTextAction().actionPerformed(e);

		int indexOfSelectedTab = ((DefaultMultipleDocumentModel) model).getSelectedIndex();
		JTextArea editor = model.getDocument(indexOfSelectedTab).getTextComponent();
		Document document = editor.getDocument();

		int selectionLength = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;

		if (selectionLength == 0) {
			return;
		}

		offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			document.remove(offset, selectionLength);
		} catch (BadLocationException el) {
			el.printStackTrace();
		}
	}

}
