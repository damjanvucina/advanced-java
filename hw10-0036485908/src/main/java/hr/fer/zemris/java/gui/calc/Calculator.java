package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static java.lang.Math.PI;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String CALC_SCREEN = "screen";
	private static final Color SCREEN_COLOR = Color.ORANGE;
	private static final Color BORDER_COLOR = Color.BLACK;
	private static final Color BUTTON_COLOR = Color.decode("#4C8DAD");

	CalcModelImpl model;
	Screen screen;
	boolean checkboxInv;
	DigitButton test;

	public Calculator() {
		setLocation(50, 50);
		setSize(600, 600);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	private void initGUI() {
		model = new CalcModelImpl();

		Container cp = getContentPane();
		JPanel p = new JPanel(new CalcLayout(5));

		p = fillPanel(p);
		createCalculatorGUI(p);

		cp.add(p);
	}

	private JPanel fillPanel(JPanel p) {
		screen = new Screen("0");
		p.add(screen, new RCPosition(1, 1));

		addDigitButtons(p);

		addUnaryButtons(p);

		addBinaryButtons(p);

		p.add(new JButton("="), new RCPosition(1, 6));
		p.add(new JButton("clr"), new RCPosition(1, 7));
		p.add(new JButton("1/x"), new RCPosition(2, 1));
		p.add(new JButton("res"), new RCPosition(2, 7));
		p.add(new JButton("log"), new RCPosition(3, 1));
		p.add(new JButton("push"), new RCPosition(3, 7));
		p.add(new JButton("ln"), new RCPosition(4, 1));
		p.add(new JButton("pop"), new RCPosition(4, 7));
		p.add(new JButton("x^n"), new RCPosition(5, 1));
		p.add(new JButton("+/-"), new RCPosition(5, 4));
		p.add(new JButton("."), new RCPosition(5, 5));
		p.add(new JCheckBox("Inv"), new RCPosition(5, 7));

		return p;
	}

	private void addBinaryButtons(JPanel p) {
		p.add(new JButton("+"), new RCPosition(5, 6));
		p.add(new JButton("-"), new RCPosition(4, 6));
		p.add(new JButton("*"), new RCPosition(3, 6));
		p.add(new JButton("/"), new RCPosition(2, 6));
	}

	private void addUnaryButtons(JPanel p) {
		p.add(new UnaryButton("sin", Math::sin, Math::asin), new RCPosition(2, 2));
		p.add(new UnaryButton("cos", Math::cos, Math::acos), new RCPosition(3, 2));
		p.add(new UnaryButton("tan", Math::tan, Math::atan), new RCPosition(4, 2));
		p.add(new UnaryButton("ctg", x -> 1 / Math.tan(x), x -> PI / 2 - Math.atan(x)), new RCPosition(5, 2));
	}

	private void addDigitButtons(JPanel p) {
		p.add(new DigitButton("0"), new RCPosition(5, 3));
		p.add(new DigitButton("1"), new RCPosition(4, 3));
		p.add(new DigitButton("2"), new RCPosition(4, 4));
		p.add(new DigitButton("3"), new RCPosition(4, 5));
		p.add(new DigitButton("4"), new RCPosition(3, 3));
		p.add(new DigitButton("5"), new RCPosition(3, 4));
		p.add(new DigitButton("6"), new RCPosition(3, 5));
		p.add(new DigitButton("7"), new RCPosition(2, 3));
		p.add(new DigitButton("8"), new RCPosition(2, 4));
		p.add(new DigitButton("9"), new RCPosition(2, 5));
	}

	private class UnaryButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		String value;
		DoubleUnaryOperator regular;
		DoubleUnaryOperator inverted;

		public UnaryButton(String value, DoubleUnaryOperator regular, DoubleUnaryOperator inverted) {
			this.value = value;
			this.regular = regular;
			this.inverted = inverted;
			setText(value);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			//Calculator.this.model.
		}

	}

	private class DigitButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		int value;

		public DigitButton(String value) {
			this.value = Integer.parseInt(value);
			addActionListener(this);
			setText(value);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			Calculator.this.model.insertDigit(value);
		}

	}

	private class Screen extends JLabel implements CalcValueListener {
		private static final long serialVersionUID = 1L;

		public Screen(String text) {
			this.setText(text);
			model.addCalcValueListener(this);
		}

		@Override
		public void valueChanged(CalcModel model) {
			this.setText(model.toString());
		}

	}

	private void createCalculatorGUI(JPanel p) {
		for (Component component : p.getComponents()) {
			if (component instanceof JLabel) {
				JLabel currentLabel = (JLabel) component;
				currentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				currentLabel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentLabel.setOpaque(true);
				currentLabel.setBackground(SCREEN_COLOR);

			} else if (component instanceof JButton) {
				JButton currentButton = (JButton) component;
				currentButton.setHorizontalAlignment(SwingConstants.CENTER);
				currentButton.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentButton.setOpaque(true);
				currentButton.setBackground(BUTTON_COLOR);

			} else {
				JCheckBox currentCheckbox = (JCheckBox) component;
				currentCheckbox.setBackground(BUTTON_COLOR);
				currentCheckbox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentCheckbox.setBorderPainted(true);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			// frame.pack();
			frame.setVisible(true);

			
		});
	}
}
