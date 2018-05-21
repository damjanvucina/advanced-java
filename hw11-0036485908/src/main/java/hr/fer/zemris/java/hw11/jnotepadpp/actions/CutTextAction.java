package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class CutTextAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public CutTextAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(model.getCurrentDocument() == null) {
			JOptionPane.showMessageDialog(window, "Document not found.", "Cut error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		window.getCopyTextAction().actionPerformed(e);

		JTextArea editor = model.getCurrentDocument().getTextComponent();
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
