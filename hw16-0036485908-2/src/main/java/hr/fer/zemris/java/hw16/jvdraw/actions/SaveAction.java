package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class responsible for updating the currently drawn image.
 * 
 * @author Damjan Vučina
 */
public class SaveAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The main window. */
	private JVDraw window;

	/**
	 * Instantiates a new save action.
	 *
	 * @param window
	 *            the window
	 */
	public SaveAction(JVDraw window) {
		this.window = window;
	}

	/**
	 * Method invoked when save action occured. Updates the currently drawn image.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (window.getImagePath() == null) {
			window.getSaveAsAction().actionPerformed(e);
			return;
		}

		UtilityProvider.saveJVD(window.getImagePath(), window.getDocumentModel().getObjects());
	}

}
