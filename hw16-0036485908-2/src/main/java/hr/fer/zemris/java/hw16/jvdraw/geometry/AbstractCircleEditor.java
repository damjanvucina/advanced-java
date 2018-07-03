package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AbstractCircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;

	private GeometricalObject object;
	private JTextField centerX;
	private JTextField centerY;
	private JTextField radius;
	private JTextField fgColor;

	public AbstractCircleEditor(GeometricalObject object) {
		this.object = object;
	}

	protected void setUp() {
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

		JPanel fgColorPanel = new JPanel();
		fgColorPanel.setBorder(BorderFactory.createTitledBorder("Foreground"));

		fgColorPanel.add(new JLabel("color:"));
		fgColorPanel.add(fgColor);

		add(centerPanel);
		add(radiusPanel);
		add(fgColorPanel);
	}

	protected void initialize() {
		centerX = new JTextField(String.valueOf(object.getStartPoint().x));
		centerY = new JTextField(String.valueOf(object.getStartPoint().y));
		radius = new JTextField(String.valueOf(object.calculatePointsDistance()));

		fgColor = new JTextField(colorToHexString(object.getFgColor()));
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
		object.setStartPoint(new Point(Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText())));
		object.setEndPoint(new Point(Integer.parseInt(centerX.getText()),
									 Integer.parseInt(centerY.getText()) + Integer.parseInt(radius.getText())));
		
		object.setFgColor(Color.decode(fgColor.getText()));
		
		object.notifyListeners();
	}
	//@formatter:on
}
