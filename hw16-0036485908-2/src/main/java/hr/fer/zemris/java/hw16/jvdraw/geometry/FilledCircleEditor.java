package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilledCircleEditor extends CircleEditor {
	private static final long serialVersionUID = 1L;

	private FilledCircle filledCircle;
	private JTextField bgColor;

	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		this.filledCircle = filledCircle;
		
		initializeFilledCircle();
		setUpFilledCircle();
	}
	
	protected void initializeFilledCircle() {
		bgColor = new JTextField(colorToHexString(filledCircle.getBgColor()));
	}
	
	protected void setUpFilledCircle() {
		JPanel bgColorPanel = new JPanel();
		bgColorPanel.setBorder(BorderFactory.createTitledBorder("Background"));

		bgColorPanel.add(new JLabel("color:"));
		bgColorPanel.add(bgColor);
		
		add(bgColorPanel);
	}

	@Override
	public void checkEditing() {
		super.checkEditing();
		
		validateColorValue(bgColor.getText());
	}

	@Override
	public void acceptEditing() {
		filledCircle.setBgColor(Color.decode(bgColor.getText()));
		
		super.acceptEditing();
	}

}
