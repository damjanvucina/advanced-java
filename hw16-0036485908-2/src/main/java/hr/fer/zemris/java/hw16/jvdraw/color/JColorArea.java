package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

public class JColorArea extends JComponent implements IColorProvider {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_DIMENSION = 15;

	private Color selectedColor;
	private List<ColorChangeListener> listeners;

	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		
		listeners = new ArrayList<>();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Please choose color", selectedColor);
				if(newColor == null) {
					return;
				}
				
				Color oldColor = selectedColor;
				setSelectedColor(newColor);
				repaint();
				
				notifyListeners(oldColor, newColor);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.fillRect(getX(), getY(), DEFAULT_DIMENSION, DEFAULT_DIMENSION);
	}

	private void notifyListeners(Color oldColor, Color newColor) {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, newColor);
		}
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_DIMENSION, DEFAULT_DIMENSION);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Cannot add null listener.");

		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		Objects.requireNonNull(l, "Cannot remove null listener.");

		listeners.remove(l);
	}

}
