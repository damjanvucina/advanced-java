package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveAction.
 */
public class SaveAction extends AbstractAction{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The window. */
	private JVDraw window;
	
	/**
	 * Instantiates a new save action.
	 *
	 * @param window the window
	 */
	public SaveAction(JVDraw window) {
		this.window = window;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(window.getImagePath() == null) {
			window.getSaveAsAction().actionPerformed(e);
			return;
		}
		
		UtilityProvider.saveJVD(window.getImagePath(), window.getDocumentModel().getObjects());
	}

}
