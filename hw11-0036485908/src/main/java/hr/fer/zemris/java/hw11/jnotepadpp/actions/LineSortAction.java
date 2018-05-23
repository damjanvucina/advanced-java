package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.DefaultSingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class LineSortAction extends AbstractAction implements SingleDocumentListener {
	private static final long serialVersionUID = 1L;
	private final String ASC = "ascending";

	private MultipleDocumentModel model;
	private String direction;
	private FormLocalizationProvider flp;

	public LineSortAction(FormLocalizationProvider flp, MultipleDocumentModel model,
			String direction) {
		this.model = model;
		this.direction = direction;
		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int indexOfSelectedTab = ((DefaultMultipleDocumentModel) model).getSelectedIndex();
		JTextArea editor = model.getDocument(indexOfSelectedTab).getTextComponent();
		Document document = editor.getDocument();

		int selectionStart = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int selectionEnd = Math.max(editor.getCaret().getDot(), editor.getCaret().getMark());

		int linesStart = editor.getText().substring(0, selectionStart).lastIndexOf("\n") + 1;
		int linesEnd = editor.getText().substring(selectionEnd, editor.getText().length()).indexOf("\n") - 1
				+ selectionEnd;

		String view = null;
		try {
			view = document.getText(linesStart, linesEnd - linesStart);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		List<String> viewList = Arrays.asList(view.split("\n"));
		Locale locale = new Locale(flp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);

		if (ASC.equals(direction)) {
			Collections.sort(viewList, collator);
		} else {
			Collections.sort(viewList, collator.reversed());
		}

		StringBuilder sb = new StringBuilder(viewList.size());
		for (String s : viewList) {
			sb.append(s).append("\n");
		}
		sb.delete(sb.lastIndexOf("\n"), sb.length());

		try {
			document.remove(linesStart, linesEnd - linesStart);
			document.insertString(linesStart, sb.toString(), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		setEnabled(false);
	}

	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		if (((DefaultSingleDocumentModel) model).getSelectionLength() > 0) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
	}

}
