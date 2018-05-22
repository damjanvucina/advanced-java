package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge{

	public FormLocalizationProvider(ILocalizationProvider iLocalizationProvider, JFrame frame) {
		super(iLocalizationProvider);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				disconnect();
			}
		});
	}

}
