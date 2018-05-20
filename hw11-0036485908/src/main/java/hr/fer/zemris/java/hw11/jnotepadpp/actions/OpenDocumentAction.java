package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

public class OpenDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	MultipleDocumentModel model;
	JNotepadPP window;

	public OpenDocumentAction(JNotepadPP window, MultipleDocumentModel model) {
		this.window = window;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		
		if(fc.showOpenDialog(window) != JFileChooser.APPROVE_OPTION){
			return;
		}
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(window, "File " + fileName.getAbsolutePath() + " does not exist.",
			"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		model.loadDocument(filePath);
	}

}
