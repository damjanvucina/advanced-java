package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

public class ShowStatsAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	MultipleDocumentModel model;
	JNotepadPP window;
	FormLocalizationProvider flp;

	public ShowStatsAction(JNotepadPP window, MultipleDocumentModel model, FormLocalizationProvider flp) {
		this.window = window;
		this.model = model;
		this.flp = flp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();

		int numOfAllChars = editor.getText().getBytes().length;
		int numOfNonBlankChars = editor.getText().replace(" ", "").getBytes().length;
		int numOfLines = editor.getText().split("\n").length;

		//@formatter:off
//		JOptionPane.showMessageDialog(window,
//									  "Your document contains " +  numOfAllChars + " characters, " + 
//									  numOfNonBlankChars + " non-blank characters and " +
//									  numOfLines + " lines.", "Document Stats", JOptionPane.INFORMATION_MESSAGE);
		
		JOptionPane.showMessageDialog(window,
				  flp.getString("numOfAllChars") +  numOfAllChars + "\n" + 
						  flp.getString("numOfNonBlankChars") + numOfNonBlankChars + "\n" +
						  flp.getString("numOfLines") + numOfLines, flp.getString("documentStats"), JOptionPane.INFORMATION_MESSAGE);
		//@formatter:on
	}

}
