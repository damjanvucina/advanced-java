package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

public class SaveAsAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	private JVDraw window;
	
	public SaveAsAction(JVDraw window) {
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
