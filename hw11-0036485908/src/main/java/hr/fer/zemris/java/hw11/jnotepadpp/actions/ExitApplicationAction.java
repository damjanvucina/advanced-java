package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class ExitApplicationAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;

	public ExitApplicationAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0, length = model.getNumberOfDocuments(); i < length; i++) {
			window.getCloseTabAction().closeTab(i);
		}
		
		System.exit(0);
	}

}
