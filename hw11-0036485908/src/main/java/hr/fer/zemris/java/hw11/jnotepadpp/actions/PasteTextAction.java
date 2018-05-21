package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class PasteTextAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public PasteTextAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		String clipboard = ((DefaultMultipleDocumentModel) model).getClipboard();
		
		try {
			document.insertString(editor.getCaretPosition(), clipboard, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
