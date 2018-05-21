package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class CreateNewDocumentAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	
	MultipleDocumentModel model;
	JNotepadPP window;

	public CreateNewDocumentAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.createNewDocument();
		
		window.getAvailableActionValidator().actionPerformed(e);
		
		DefaultMultipleDocumentModel defaultModel = ((DefaultMultipleDocumentModel) model);
		defaultModel.setIconAt(defaultModel.getDocuments().indexOf(defaultModel.getCurrentDocument()), window.acquireIcon("notsaved.png"));
	}

}
