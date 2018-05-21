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

public class CopyTextAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public CopyTextAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		
		int selectionLength = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());	
		int offset=0;
		if (selectionLength == 0) {
			JOptionPane.showMessageDialog(window, "Text selectio not found.", "Copy error", JOptionPane.ERROR_MESSAGE);
			return;
			
		} else {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		}
		
		String text = null;
		try {
			text = document.getText(offset, selectionLength);
			((DefaultMultipleDocumentModel) model).setClipboard(text);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
