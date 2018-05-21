package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IconListener implements DocumentListener {
	
	private DefaultMultipleDocumentModel defaultModel;
	private int tabIndex;
	private ImageIcon notSavedIcon;
	private DefaultSingleDocumentModel document;

	public IconListener(DefaultMultipleDocumentModel defaultModel, int tabIndex, ImageIcon notSavedIcon, SingleDocumentModel newDocument) {
		this.defaultModel = defaultModel;
		this.tabIndex = tabIndex;
		this.notSavedIcon = notSavedIcon;
		this.document = (DefaultSingleDocumentModel) newDocument;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		setNotSavedIcon();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		setNotSavedIcon();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		setNotSavedIcon();
	}
	
	private void setNotSavedIcon() {
		defaultModel.setIconAt(tabIndex, notSavedIcon);
		document.setModified(true);
	}

}
