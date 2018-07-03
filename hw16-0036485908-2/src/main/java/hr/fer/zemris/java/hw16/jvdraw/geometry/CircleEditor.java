package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CircleEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = 1L;

	private Circle circle;
	private JTextField centerX;
	private JTextField centerY;
	private JTextField radius;
	private JTextField fgColor;

	public CircleEditor(Circle circle) {
		this.circle = circle;

		initialize();
		setUp();
	}

	private void setUp() {
		setLayout(new GridLayout(1, 0));

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createTitledBorder("Edit Center"));

		centerPanel.add(new JLabel("x:"));
		centerPanel.add(centerX);
		centerPanel.add(new JLabel("y:"));
		centerPanel.add(centerY);

		JPanel radiusPanel = new JPanel();
		radiusPanel.setBorder(BorderFactory.createTitledBorder("Edit Radius"));

		radiusPanel.add(new JLabel("r:"));
		radiusPanel.add(radius);

		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory.createTitledBorder("Edit Foreground Color"));

		colorPanel.add(new JLabel("color:"));
		colorPanel.add(fgColor);

		add(centerPanel);
		add(radiusPanel);
		add(colorPanel);
	}

	private void initialize() {
		centerX = new JTextField(String.valueOf(circle.getCenter().x));
		centerY = new JTextField(String.valueOf(circle.getCenter().y));
		radius = new JTextField(String.valueOf(circle.calculateRadius()));

		fgColor = new JTextField(colorToHexString(circle.getFgColor()));
	}

	@Override
	public void checkEditing() {
		validateCoordinates(centerX.getText());
		validateCoordinates(centerY.getText());
		validateRadius(radius.getText());
		validateColorValue(fgColor.getText());
	}

	//@formatter:off
	@Override
	public void acceptEditing() {
		circle.setStartPoint(new Point(Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText())));
		circle.setEndPoint(new Point(Integer.parseInt(centerX.getText()),
									 Integer.parseInt(centerY.getText()) + Integer.parseInt(radius.getText())));
		
		circle.setFgColor(Color.decode(fgColor.getText()));
		
		circle.notifyListeners();
	}
	//@formatter:on
}
