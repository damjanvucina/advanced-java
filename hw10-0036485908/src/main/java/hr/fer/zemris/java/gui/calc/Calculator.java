package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static java.lang.Math.PI;
import static java.lang.Math.E;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Color SCREEN_COLOR = Color.ORANGE;
	private static final Color BORDER_COLOR = Color.BLACK;
	private static final Color BUTTON_COLOR = Color.decode("#4C8DAD");

	private CalcModelImpl model;
	private Screen screen;
	private boolean invertedTicked;
	private Map<UnaryButton, String> buttonsRegular;
	private Map<UnaryButton, String> buttonsInverted;
	private Stack<Double> stack;

	public Calculator() {
		model = new CalcModelImpl();
		buttonsRegular = new HashMap<>();
		buttonsInverted = new HashMap<>();
		stack = new Stack<>();

		setLocation(50, 50);
		setSize(600, 600);
		setTitle("CASIO fx-991ES PLUS");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	private void initGUI() {
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

		addSpecialButtons(p);

		p.add(new JButton("x^n"), new RCPosition(5, 1));

		return p;
	}

	private void addSpecialButtons(JPanel p) {
		JCheckBox invCheckBox = new JCheckBox("Inv");
		invCheckBox.addActionListener(l -> invertDualButtonsNames());
		p.add(invCheckBox, new RCPosition(5, 7));

		JButton clr = new JButton("clr");
		clr.addActionListener(l -> model.clear());
		p.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.addActionListener(l -> model.clearAll());
		p.add(res, new RCPosition(2, 7));

		JButton push = new JButton("push");
		push.addActionListener(l -> stack.push(model.getValue()));
		p.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.addActionListener(l -> {
			if (!stack.isEmpty()) {
				model.setValue(stack.pop());

			} else {
				JOptionPane.showMessageDialog(this, "Cannot pop from empty stack.", "Empty stack",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		p.add(pop, new RCPosition(4, 7));

		JButton reciprocal = new JButton("1/x");
		reciprocal.addActionListener(l -> model.setValue(1 / model.getValue()));
		p.add(reciprocal, new RCPosition(2, 1));

		JButton point = new JButton(".");
		point.addActionListener(l -> model.insertDecimalPoint());
		p.add(point, new RCPosition(5, 5));

		JButton signSwapper = new JButton("+/-");
		signSwapper.addActionListener(l -> model.swapSign());
		p.add(signSwapper, new RCPosition(5, 4));
	}

	private void invertDualButtonsNames() {
		if (invertedTicked == false) {
			invertedTicked = true;
			buttonsInverted.forEach((btn, name) -> btn.setText(name));
		} else {
			invertedTicked = false;
			buttonsRegular.forEach((btn, name) -> btn.setText(name));
		}

		revalidate();
	}

	private void addBinaryButtons(JPanel p) {
		BinaryButton addition = new BinaryButton("+", (x, y) -> x + y);
		p.add(addition, new RCPosition(5, 6));

		BinaryButton subtraction = new BinaryButton("-", (x, y) -> x - y);
		p.add(subtraction, new RCPosition(4, 6));

		BinaryButton multiplication = new BinaryButton("*", (x, y) -> x * y);
		p.add(multiplication, new RCPosition(3, 6));

		BinaryButton division = new BinaryButton("/", (x, y) -> x / y);
		p.add(division, new RCPosition(2, 6));

		BinaryButton equalsSymbol = new BinaryButton("=", (x, y) -> 0);
		p.add(equalsSymbol, new RCPosition(1, 6));
	}

	private void addUnaryButtons(JPanel p) {
		UnaryButton sin = new UnaryButton("sin", Math::sin, Math::asin);
		p.add(sin, new RCPosition(2, 2));
		buttonsRegular.put(sin, "sin");
		buttonsInverted.put(sin, "asin");

		UnaryButton cos = new UnaryButton("cos", Math::cos, Math::acos);
		p.add(cos, new RCPosition(3, 2));
		buttonsRegular.put(cos, "cos");
		buttonsInverted.put(cos, "acos");

		UnaryButton tan = new UnaryButton("tan", Math::tan, Math::atan);
		p.add(tan, new RCPosition(4, 2));
		buttonsRegular.put(tan, "tan");
		buttonsInverted.put(tan, "atan");

		UnaryButton ctg = new UnaryButton("ctg", x -> 1 / Math.tan(x), x -> PI / 2 - Math.atan(x));
		p.add(ctg, new RCPosition(5, 2));
		buttonsRegular.put(ctg, "ctg");
		buttonsInverted.put(ctg, "actg");

		UnaryButton log = new UnaryButton("log", x -> Math.log10(x), x -> Math.pow(10, x));
		p.add(log, new RCPosition(3, 1));
		buttonsRegular.put(log, "log");
		buttonsInverted.put(log, "10^x");

		UnaryButton ln = new UnaryButton("ln", x -> Math.log(x), x -> Math.pow(E, x));
		p.add(ln, new RCPosition(4, 1));
		buttonsRegular.put(ln, "ln");
		buttonsInverted.put(ln, "e^x");
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

	private class BinaryButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		String value;
		DoubleBinaryOperator operation;

		public BinaryButton(String value, DoubleBinaryOperator operation) {
			this.value = value;
			this.operation = operation;
			setText(value);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			CalcModelImpl model = Calculator.this.model;
			double currentNumber = Calculator.this.model.getValue();
			
			if (!model.isActiveOperandSet()) {
				performSuccesiveCalculation(model, currentNumber);

			} else {
				//@formatter:off
				double result = model.getPendingBinaryOperation()
									 .applyAsDouble(model.getActiveOperand(), currentNumber);
				//@formatter:on
				model.setValue(result);
				performSuccesiveCalculation(model, result);
			}
			
			if(this.value == "=") {
				model.clearAllWithoutNotifying();
			}
		}

		private void performSuccesiveCalculation(CalcModelImpl model, double value) {
			model.setActiveOperand(value);
			model.setPendingBinaryOperation(operation);
			model.clearWithoutNotifying();
		}
	}

	private class UnaryButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		String value;
		DoubleUnaryOperator regular;
		DoubleUnaryOperator inverted;

		public UnaryButton(String value, DoubleUnaryOperator regular, DoubleUnaryOperator inverted) {
			this.value = value;
			this.regular = regular;
			this.inverted = inverted;
			addActionListener(this);
			setText(value);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			CalcModelImpl model = Calculator.this.model;
			double currentNumber = Calculator.this.model.getValue();

			if (invertedTicked == false) {
				model.setValue(regular.applyAsDouble(currentNumber));
			} else {
				model.setValue(inverted.applyAsDouble(currentNumber));
			}
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
