package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

public class SaveAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	private JVDraw window;
	
	public SaveAction(JVDraw window) {
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(window.getImagePath() == null) {
			window.getSaveAsAction().actionPerformed(e);
			return;
		}
		
		UtilityProvider.saveJVD(window.getImagePath(), window.getDocumentModel().getObjects());
	}

}
