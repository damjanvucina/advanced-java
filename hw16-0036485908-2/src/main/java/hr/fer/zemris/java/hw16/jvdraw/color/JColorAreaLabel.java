package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

public class JColorAreaLabel extends JLabel implements ColorChangeListener{
	private static final long serialVersionUID = 1L;
	public static final String FOREGROUND_COLOR="Foreground color";
	public static final String BACKGROUND_COLOR="Background color";
	
	private IColorProvider fgColorProvider;
	private IColorProvider bgColorProvider;
	private String text;

	public JColorAreaLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
	}

	public String getText() {
		return text;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText(oldColor, newColor);
	}

	private void updateText(Color oldColor, Color newColor) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(FOREGROUND_COLOR).append(": ");
		sb.append(fgColorProvider.toString()).append(", ");
		sb.append(BACKGROUND_COLOR).append(": ");
		sb.append(bgColorProvider.toString()).append(".");
		
		text = sb.toString();
	}
}
