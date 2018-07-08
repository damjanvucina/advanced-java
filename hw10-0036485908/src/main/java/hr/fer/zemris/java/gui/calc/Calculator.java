package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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

/**
 * The class representing a calculator window. It provides user with standard
 * mathematical operations such as addition, subtraction, multiplication and
 * division as well as advenced operations such as trigonometry and logarithmic
 * operations. Details of operations are given as button tooltips.
 * 
 * @author Damjan Vučina
 */
public class Calculator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SCREEN_COLOR. */
	private static final Color SCREEN_COLOR = Color.ORANGE;

	/** The Constant BORDER_COLOR. */
	private static final Color BORDER_COLOR = Color.BLACK;

	/** The Constant BUTTON_COLOR. */
	private static final Color BUTTON_COLOR = Color.decode("#4C8DAD");

	/** The Constant NO_INVERTED_OPERATION. */
	private static final DoubleBinaryOperator NO_INVERTED_OPERATION = null;

	/** The Constant BUTTON_FONT. */
	private static final Font BUTTON_FONT = new Font("ARIAL", Font.BOLD, 20);

	/** The Constant SCREEN_FONT. */
	private static final Font SCREEN_FONT = new Font("ARIAL", Font.BOLD, 30);

	/** The Constant MINIMUM_SIZE. */
	private static final Dimension MINIMUM_SIZE = new Dimension(500, 500);

	/** The Constant CLR_TOOLTIP. */
	private static final String CLR_TOOLTIP = "Clears current entry";

	/** The Constant RES_TOOLTIP. */
	private static final String RES_TOOLTIP = "Restarts calculator";

	/** The Constant PUSH_TOOLTIP. */
	private static final String PUSH_TOOLTIP = "Pushes current entry to the stack";

	/** The Constant POP_TOOLTIP. */
	private static final String POP_TOOLTIP = "Pops last pushed entry from the stack";

	/** The Constant INV_TOOLTIP. */
	private static final String INV_TOOLTIP = "Displays advanced math functions";

	/** The Constant RECIPROCAL_TOOLTIP. */
	private static final String RECIPROCAL_TOOLTIP = "Calculates reciprocal value of current entry";

	/** The Constant LOG_TOOLTIP. */
	private static final String LOG_TOOLTIP = "Calculates 10 base logarithm / 10 base power";

	/** The Constant LN_TOOLTIP. */
	private static final String LN_TOOLTIP = "Calculates e base logarithm / e base power";

	/** The Constant POWER_TOOLTIP. */
	private static final String POWER_TOOLTIP = "Calculates n-th power/root of current entry";

	/** The Constant SWAP_SIGN_TOOLTIP. */
	private static final String SWAP_SIGN_TOOLTIP = "Changes arithmetic sign";

	/** The Constant SIN_TOOLTIP. */
	private static final String SIN_TOOLTIP = "Calculates sine/inverse sine of current entry";

	/** The Constant COS_TOOLTIP. */
	private static final String COS_TOOLTIP = "Calculates cosine/inverse cosine of current entry";

	/** The Constant TAN_TOOLTIP. */
	private static final String TAN_TOOLTIP = "Calculates tangent/inverse tangent of current entry";

	/** The Constant CTG_TOOLTIP. */
	private static final String CTG_TOOLTIP = "Calculates cotangent/inverse cotangent of current entry";

	/** The model used for processing. */
	private CalcModelImpl model;

	/** The screen of the calculator. */
	private Screen screen;

	/** The flag stating that advanced mathematical functions are displayed. */
	private boolean invertedTicked;

	/** The map used for transforming the text on the multiple purpose buttons. */
	private Map<JButton, String> buttonsRegular;

	/** The map used for transforming the text on the multiple purpose buttons. */
	private Map<JButton, String> buttonsInverted;

	/** The stack. */
	private Stack<Double> stack;

	/**
	 * Instantiates a new calculator.
	 */
	public Calculator() {
		model = new CalcModelImpl();
		buttonsRegular = new HashMap<>();
		buttonsInverted = new HashMap<>();
		stack = new Stack<>();

		setLocation(50, 50);
		setSize(800, 400);
		setMinimumSize(MINIMUM_SIZE);
		setTitle("CASIO fx-991ES PLUS");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes the GUI of this calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		JPanel p = new JPanel(new CalcLayout(5));

		p = fillPanel(p);
		createCalculatorGUI(p);

		cp.add(p);
	}

	/**
	 * Fills the calculator panel.
	 *
	 * @param p
	 *            the p
	 * @return the j panel
	 */
	private JPanel fillPanel(JPanel p) {
		screen = new Screen("0");
		p.add(screen, new RCPosition(1, 1));

		addDigitButtons(p);

		addUnaryButtons(p);

		addBinaryButtons(p);

		addSpecialButtons(p);

		return p;
	}

	/**
	 * Adds the special buttons to the calculator and defines their functionalities.
	 *
	 * @param p
	 *            the p
	 */
	private void addSpecialButtons(JPanel p) {
		JCheckBox invCheckBox = new JCheckBox("Inv");
		invCheckBox.setToolTipText(INV_TOOLTIP);
		invCheckBox.addActionListener(l -> invertDualButtonsNames());
		p.add(invCheckBox, new RCPosition(5, 7));

		JButton clr = new JButton("clr");
		clr.setToolTipText(CLR_TOOLTIP);
		clr.addActionListener(l -> model.clear());
		p.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.setToolTipText(RES_TOOLTIP);
		res.addActionListener(l -> model.clearAll());
		p.add(res, new RCPosition(2, 7));

		JButton push = new JButton("push");
		push.setToolTipText(PUSH_TOOLTIP);
		push.addActionListener(l -> stack.push(model.getValue()));
		p.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.setToolTipText(POP_TOOLTIP);
		pop.addActionListener(l -> {
			if (!stack.isEmpty()) {
				model.setValue(stack.pop());

			} else {
				showWarningMessage("Cannot pop from empty stack.");
			}
		});
		p.add(pop, new RCPosition(4, 7));

		JButton reciprocal = new JButton("1/x");
		reciprocal.setToolTipText(RECIPROCAL_TOOLTIP);

		reciprocal.addActionListener(l -> {
			if (model.getValue() == 0) {
				showWarningMessage("Cannot divide by zero.");
			} else {
				model.setValue(1 / model.getValue());
			}

		});

		p.add(reciprocal, new RCPosition(2, 1));

		JButton point = new JButton(".");
		point.addActionListener(l -> model.insertDecimalPoint());
		p.add(point, new RCPosition(5, 5));

		JButton signSwapper = new JButton("+/-");
		signSwapper.setToolTipText(SWAP_SIGN_TOOLTIP);
		signSwapper.addActionListener(l -> model.swapSign());
		p.add(signSwapper, new RCPosition(5, 4));
	}

	/**
	 * Inverts dual buttons names.
	 */
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

	/**
	 * Adds the binary buttons to the calculator and defines their functionalities.
	 *
	 * @param p
	 *            the p
	 */
	private void addBinaryButtons(JPanel p) {
		BinaryButton addition = new BinaryButton("+", (x, y) -> x + y, NO_INVERTED_OPERATION);
		p.add(addition, new RCPosition(5, 6));

		BinaryButton subtraction = new BinaryButton("-", (x, y) -> x - y, NO_INVERTED_OPERATION);
		p.add(subtraction, new RCPosition(4, 6));

		BinaryButton multiplication = new BinaryButton("*", (x, y) -> x * y, NO_INVERTED_OPERATION);
		p.add(multiplication, new RCPosition(3, 6));

		BinaryButton division = new BinaryButton("/", (x, y) -> x / y, NO_INVERTED_OPERATION);
		p.add(division, new RCPosition(2, 6));

		BinaryButton equalsSymbol = new BinaryButton("=", (x, y) -> 0, NO_INVERTED_OPERATION);
		p.add(equalsSymbol, new RCPosition(1, 6));

		BinaryButton power = new BinaryButton("x^n", (x, y) -> Math.pow(x, y), (x, y) -> Math.pow(x, 1 / y));
		power.setToolTipText(POWER_TOOLTIP);
		p.add(power, new RCPosition(5, 1));
		buttonsRegular.put(power, "x^n");
		buttonsInverted.put(power, "x^1/n");
	}

	/**
	 * Adds the unary buttons to the calculator and defines their functionalities.
	 *
	 * @param p
	 *            the p
	 */
	private void addUnaryButtons(JPanel p) {
		UnaryButton sin = new UnaryButton("sin", Math::sin, Math::asin);
		sin.setToolTipText(SIN_TOOLTIP);
		p.add(sin, new RCPosition(2, 2));
		buttonsRegular.put(sin, "sin");
		buttonsInverted.put(sin, "asin");

		UnaryButton cos = new UnaryButton("cos", Math::cos, Math::acos);
		cos.setToolTipText(COS_TOOLTIP);
		p.add(cos, new RCPosition(3, 2));
		buttonsRegular.put(cos, "cos");
		buttonsInverted.put(cos, "acos");

		UnaryButton tan = new UnaryButton("tan", Math::tan, Math::atan);
		tan.setToolTipText(TAN_TOOLTIP);
		p.add(tan, new RCPosition(4, 2));
		buttonsRegular.put(tan, "tan");
		buttonsInverted.put(tan, "atan");

		UnaryButton ctg = new UnaryButton("ctg", x -> 1 / Math.tan(x), x -> PI / 2 - Math.atan(x));
		ctg.setToolTipText(CTG_TOOLTIP);
		p.add(ctg, new RCPosition(5, 2));
		buttonsRegular.put(ctg, "ctg");
		buttonsInverted.put(ctg, "actg");

		UnaryButton log = new UnaryButton("log", x -> Math.log10(x), x -> Math.pow(10, x));
		log.setToolTipText(LOG_TOOLTIP);
		p.add(log, new RCPosition(3, 1));
		buttonsRegular.put(log, "log");
		buttonsInverted.put(log, "10^x");

		UnaryButton ln = new UnaryButton("ln", x -> x = Math.log(x), x -> Math.pow(E, x));
		ln.setToolTipText(LN_TOOLTIP);
		p.add(ln, new RCPosition(4, 1));
		buttonsRegular.put(ln, "ln");
		buttonsInverted.put(ln, "e^x");
	}

	/**
	 * Adds the digit buttons to the calculator.
	 *
	 * @param p
	 *            the p
	 */
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

	/**
	 * The Class BinaryButton.
	 */
	private class BinaryButton extends JButton implements ActionListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The value. */
		String value;

		/** The operation. */
		DoubleBinaryOperator operation;

		/** The inverted operation. */
		DoubleBinaryOperator invertedOperation;

		/**
		 * Instantiates a new binary button.
		 *
		 * @param value
		 *            the value of the button
		 * @param operation
		 *            the operation it performs
		 * @param invertedOperation
		 *            the inverted operation it performs
		 */
		public BinaryButton(String value, DoubleBinaryOperator operation, DoubleBinaryOperator invertedOperation) {
			this.value = value;
			this.operation = operation;
			this.invertedOperation = invertedOperation;
			setText(value);
			addActionListener(this);
		}

		/**
		 * Method invoked when an calculator action occurs.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				CalcModelImpl model = Calculator.this.model;
				double currentNumber = Calculator.this.model.getValue();
				double result = model.getValue();

				if (!model.isActiveOperandSet()) {
					performSuccesiveCalculation(model, currentNumber);

				} else {
				//@formatter:off
				result = model.getPendingBinaryOperation()
									 .applyAsDouble(model.getActiveOperand(), currentNumber);
				//@formatter:on

					try {
						model.setValue(result);
						performSuccesiveCalculation(model, result);
					} catch (CalculatorException e) {
						showWarningMessage(e.getMessage());
					}

				}

				if (this.value.equals("=")) {
					model.clearAllWithoutNotifying();
					model.setValue(result);
				}
			} catch (CalculatorException e) {
				showWarningMessage(e.getMessage());
			}
		}

		/**
		 * Performs successive calculation which occurs when mathematical operators are
		 * given without equal symbol.
		 *
		 * @param model
		 *            the model
		 * @param value
		 *            the value
		 */
		private void performSuccesiveCalculation(CalcModelImpl model, double value) {
			model.setActiveOperand(value);

			if (this.value.equals("x^n") && invertedTicked == true) {
				model.setPendingBinaryOperation(invertedOperation);
			} else {
				model.setPendingBinaryOperation(operation);
			}

			model.clearWithoutNotifying();
		}
	}

	/**
	 * The Class UnaryButton.
	 */
	private class UnaryButton extends JButton implements ActionListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The value of the button. */
		@SuppressWarnings("unused")
		String value;

		/** The regular operation. */
		DoubleUnaryOperator regular;

		/** The inverted operation. */
		DoubleUnaryOperator inverted;

		/**
		 * Instantiates a new unary button.
		 *
		 * @param value
		 *            the value
		 * @param regular
		 *            the regular operation
		 * @param inverted
		 *            the inverted operation
		 */
		public UnaryButton(String value, DoubleUnaryOperator regular, DoubleUnaryOperator inverted) {
			this.value = value;
			this.regular = regular;
			this.inverted = inverted;
			addActionListener(this);
			setText(value);
		}

		/**
		 * Method invoked when an calculator unary button action occurs.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			CalcModelImpl model = Calculator.this.model;
			double currentNumber = Calculator.this.model.getValue();

			try {
				if (invertedTicked == false) {
					model.setValue(regular.applyAsDouble(currentNumber));
				} else {
					model.setValue(inverted.applyAsDouble(currentNumber));
				}
			} catch (CalculatorException e) {
				showWarningMessage(e.getMessage());
			}
		}

	}

	/**
	 * The Class DigitButton.
	 */
	private class DigitButton extends JButton implements ActionListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The value of the button. */
		int value;

		/**
		 * Instantiates a new digit button.
		 *
		 * @param value
		 *            the value of the button
		 */
		public DigitButton(String value) {
			this.value = Integer.parseInt(value);
			addActionListener(this);
			setText(value);
		}

		/**
		 * Method invoked when an calculator digit button action occurs.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				Calculator.this.model.insertDigit(value);
			} catch (CalculatorException e) {
				showWarningMessage(e.getMessage());
			}
		}

	}

	/**
	 * The Class Screen.
	 */
	private class Screen extends JLabel implements CalcValueListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new screen.
		 *
		 * @param text
		 *            the text on the scren
		 */
		public Screen(String text) {
			this.setText(text);
			model.addCalcValueListener(this);
		}

		/**
		 * Observer pattern is used in this implementation and screen is observer. This
		 * method is invoked so screen can update itself.
		 */
		@Override
		public void valueChanged(CalcModel model) {
			this.setText(model.toString());
		}

	}

	/**
	 * Creates the calculator GUI.
	 *
	 * @param p
	 *            the p
	 */
	private void createCalculatorGUI(JPanel p) {
		for (Component component : p.getComponents()) {
			if (component instanceof JLabel) {
				JLabel currentLabel = (JLabel) component;
				currentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				currentLabel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentLabel.setOpaque(true);
				currentLabel.setBackground(SCREEN_COLOR);
				currentLabel.setFont(SCREEN_FONT);

			} else if (component instanceof JButton) {
				JButton currentButton = (JButton) component;
				currentButton.setHorizontalAlignment(SwingConstants.CENTER);
				currentButton.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentButton.setOpaque(true);
				currentButton.setBackground(BUTTON_COLOR);
				currentButton.setFont(BUTTON_FONT);

			} else {
				JCheckBox currentCheckbox = (JCheckBox) component;
				currentCheckbox.setBackground(BUTTON_COLOR);
				currentCheckbox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
				currentCheckbox.setBorderPainted(true);
				currentCheckbox.setFont(BUTTON_FONT);
			}
		}

	}

	/**
	 * Shows warning message.
	 *
	 * @param message
	 *            the message
	 */
	public void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Invalid operation", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
