package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class SetLanguageAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private String language;

	public SetLanguageAction(String language) {
		this.language = language;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);
	}

}
