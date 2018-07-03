package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LineEditor extends GeometricalObjectEditor {
	private static final long serialVersionUID = 1L;

	private Line line;
	private JTextField startPointX;
	private JTextField startPointY;
	private JTextField endPointX;
	private JTextField endPointY;
	private JTextField fgColor;

	public LineEditor(Line line) {
		this.line = line;

		initialize();
		setUp();
	}

	private void initialize() {
		startPointX = new JTextField(String.valueOf(line.getStartPoint().x));
		startPointY = new JTextField(String.valueOf(line.getStartPoint().y));

		endPointX = new JTextField(String.valueOf(line.getEndPoint().x));
		endPointY = new JTextField(String.valueOf(line.getEndPoint().y));

		fgColor = new JTextField(colorToHexString(line.getFgColor()));
	}

	private void setUp() {
		setLayout(new GridLayout(1, 0));

		JPanel startPointPanel = new JPanel();
		startPointPanel.setBorder(BorderFactory.createTitledBorder("Edit Start Point"));

		startPointPanel.add(new JLabel("x:"));
		startPointPanel.add(startPointX);
		startPointPanel.add(new JLabel("y:"));
		startPointPanel.add(startPointY);

		JPanel endPointPanel = new JPanel();
		endPointPanel.setBorder(BorderFactory.createTitledBorder("Edit End Point"));

		endPointPanel.add(new JLabel("x:"));
		endPointPanel.add(endPointX);
		endPointPanel.add(new JLabel("y:"));
		endPointPanel.add(endPointY);

		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory.createTitledBorder("Edit Color"));

		colorPanel.add(new JLabel("color:"));
		colorPanel.add(fgColor);

		add(startPointPanel);
		add(endPointPanel);
		add(colorPanel);
	}

	@Override
	public void checkEditing() {
		validateCoordinates(startPointX.getText());
		validateCoordinates(startPointY.getText());
		validateCoordinates(endPointX.getText());
		validateCoordinates(endPointY.getText());

		validateColorValue(fgColor.getText());
	}

	@Override
	public void acceptEditing() {
		line.setStartPoint(new Point(Integer.parseInt(startPointX.getText()), Integer.parseInt(startPointY.getText())));
		line.setEndPoint(new Point(Integer.parseInt(endPointX.getText()), Integer.parseInt(endPointY.getText())));
		line.setFgColor(Color.decode(fgColor.getText()));
		
		line.notifyListeners();
	}

	private String colorToHexString(Color color) {
		return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

}
