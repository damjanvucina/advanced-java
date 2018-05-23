package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultSingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

public class ChangeCaseAction extends AbstractAction implements SingleDocumentListener{
	private static final long serialVersionUID = 1L;

	private MultipleDocumentModel model;
	private JNotepadPP window;
	private Function<Character, Character> format;

	//toUpperCase, toLowerCase, invertCase
	public ChangeCaseAction(JNotepadPP window, MultipleDocumentModel model, Function<Character, Character> format) {
		this.window = window;
		this.model = model;
		this.format = format;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int indexOfSelectedTab = ((DefaultMultipleDocumentModel) model).getSelectedIndex();
		JTextArea editor = model.getDocument(indexOfSelectedTab).getTextComponent();
		Document document = editor.getDocument();
		
		int selectionLength = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset=0;
		if (selectionLength == 0) {
			JOptionPane.showMessageDialog(window, "Text selection not found.", "Change case error", JOptionPane.ERROR_MESSAGE);
			return;
			
		} else {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		}
		
		char[] text = null;
		try {
			text = document.getText(offset, selectionLength).toCharArray();

		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < offset; i++) {
			text[i] = format.apply(text[i]);
		}

		try {
			document.remove(offset, selectionLength);
			document.insertString(editor.getCaretPosition(), String.valueOf(text), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		if(((DefaultSingleDocumentModel)model).getSelectionLength() > 0) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
	}

}
