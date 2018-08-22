package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class JColorAreaLabel.
 */
public class JColorAreaLabel extends JLabel implements ColorChangeListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FOREGROUND_COLOR. */
	public static final String FOREGROUND_COLOR = "Foreground color";
	
	/** The Constant BACKGROUND_COLOR. */
	public static final String BACKGROUND_COLOR = "Background color";

	/** The fg color provider. */
	private IColorProvider fgColorProvider;
	
	/** The bg color provider. */
	private IColorProvider bgColorProvider;
	
	/** The text. */
	private String text;

	/**
	 * Instantiates a new j color area label.
	 *
	 * @param fgColorProvider the fg color provider
	 * @param bgColorProvider the bg color provider
	 */
	public JColorAreaLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		
		updateText();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JLabel#getText()
	 */
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener#newColorSelected(hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider, java.awt.Color, java.awt.Color)
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Update text.
	 */
	private void updateText() {
		StringBuilder sb = new StringBuilder();

		sb.append(FOREGROUND_COLOR).append(": ");
		sb.append(fgColorProvider.toString()).append(", ");
		sb.append(BACKGROUND_COLOR).append(": ");
		sb.append(bgColorProvider.toString()).append(".");

		text = sb.toString();

		setText(text);
	}
}
