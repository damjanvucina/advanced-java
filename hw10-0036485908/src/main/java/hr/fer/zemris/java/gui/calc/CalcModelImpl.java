package hr.fer.zemris.java.gui.calc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * The implementation of the CalcModel interface which is responsible for
 * processing users input and providing screen output.
 * 
 * @author Damjan Vučina
 */
public class CalcModelImpl implements CalcModel {

	/** The Constant STARTING_ZERO. */
	public static final String STARTING_ZERO = "0";

	/** The Constant MAX_DIGITS. */
	public static final int MAX_DIGITS = 308;

	/** The screen. */
	private String screen;

	/** The active operand. */
	private String activeOperand;

	/** The pending operation. */
	private DoubleBinaryOperator pendingOperation;

	/** The observers. */
	private List<CalcValueListener> observers;

	/** The decimal format. */
	DecimalFormat decimalFormat;

	/**
	 * Instantiates a new calc model impl.
	 */
	public CalcModelImpl() {
		observers = new ArrayList<>();
		decimalFormat = new DecimalFormat("#.##########");
	}

	/**
	 * Adds the calculator value listener.
	 *
	 * @param l
	 *            the calculator value listener.
	 * @throws CalculatorException
	 *             if specifed observer is already registered.
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (observers.contains(l)) {
			throw new CalculatorException("Specifed observer is already registered.");
		}

		observers.add(l);
	}

	/**
	 * Removes the calculator value listener.
	 *
	 * @param l
	 *            the calculator value listener.
	 * @throws CalculatorException
	 *             if specifed observer is not registered.
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (!observers.contains(l)) {
			throw new CalculatorException("Specifed observer is not registered.");
		}

		observers.remove(l);
	}

	/**
	 * Gets the current value.
	 *
	 * @return the current value.
	 */
	@Override
	public double getValue() {
		return screen == null ? 0 : Double.parseDouble(screen);
	}

	/**
	 * Sets the new value.
	 *
	 * @param value
	 *            the new value
	 * @throws CalculatorException
	 *             if value is either NaN or infinity
	 */
	@Override
	public void setValue(double value) {
		validateValue(value);

		// screen = String.valueOf(value);
		screen = decimalFormat.format(value);
		notifyObservers();
	}

	/**
	 * Validates value.
	 *
	 * @param value
	 *            the value
	 * @throws CalculatorException
	 *             if value is either NaN or infinity
	 */
	private void validateValue(double value) {
		if (Double.isNaN(value)) {
			throw new CalculatorException("Value cannot be NaN.");
		}
		if (Double.isInfinite(value)) {
			throw new CalculatorException("Value cannot be infinity.");
		}
	}

	/**
	 * Clears current entry and notifies observers.
	 */
	@Override
	public void clear() {
		screen = null;
		notifyObservers();
	}

	/**
	 * Clears current entry and notifies observers without notifying the observers.
	 */
	public void clearWithoutNotifying() {
		screen = null;
	}

	/**
	 * Clears current entry and cache by restarting the calculator and notifies
	 * observers.
	 */
	@Override
	public void clearAll() {
		activeOperand = null;
		pendingOperation = null;
		clear();
	}

	/**
	 * Clears current entry and cache by restarting the calculator without notifying
	 * observers.
	 */
	public void clearAllWithoutNotifying() {
		activeOperand = null;
		pendingOperation = null;
		clearWithoutNotifying();
	}

	/**
	 * Swaps current sign.
	 */
	@Override
	public void swapSign() {
		if (screen == null) {
			return;
		}

		screen = String.valueOf(Double.parseDouble(screen) * -1);
		notifyObservers();
	}

	/**
	 * Inserts decimal point, if decimal point is already inserted has no effect.
	 */
	@Override
	public void insertDecimalPoint() {
		if (screen != null && screen.contains(".")) {
			return;
		}
		screen = (screen == null) ? "0" + String.valueOf(".") : screen + String.valueOf(".");
		notifyObservers();
	}

	/**
	 * Inserts digit.
	 *
	 * @param digit
	 *            the digit
	 * @throws CalculatorException
	 *             if input consists of multiple digits
	 */
	@Override
	public void insertDigit(int digit) {
		if (Integer.toString(digit).length() > 1) {
			throw new CalculatorException(
					"Input must be a single digit, digits entered: " + Integer.toString(digit).length());
		}

		if (STARTING_ZERO.equals(screen) && digit == 0) {
			return;
		}

		if (screen != null && screen.length() >= MAX_DIGITS) {
			return;
		}

		if (screen == null || screen.equals(STARTING_ZERO)) {
			screen = String.valueOf(digit);
		} else {
			screen = screen + String.valueOf(digit);
		}

		notifyObservers();
	}

	/**
	 * Checks if is active operand is set.
	 *
	 * @return true, if is active operand set
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	/**
	 * Gets the active operand.
	 *
	 * @return the active operand
	 */
	@Override
	public double getActiveOperand() {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set yet.");
		}

		return Double.parseDouble(activeOperand);
	}

	/**
	 * Sets the active operand.
	 *
	 * @param activeOperand
	 *            the new active operand
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		validateValue(activeOperand);

		this.activeOperand = String.valueOf(activeOperand);
	}

	/**
	 * Clears active operand.
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	/**
	 * Clears pending operation.
	 */
	public void clearPendingOperation() {
		pendingOperation = null;
	}

	/**
	 * Gets the pending binary operation.
	 *
	 * @return the pending binary operation
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * Sets the pending binary operation.
	 *
	 * @param op
	 *            the new pending binary operation
	 * @throws CalculatorException
	 *             if pending operation is to be set to null
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if (op == null) {
			throw new CalculatorException("Pending operation cannot be set to null.");
		}

		pendingOperation = op;
	}

	/**
	 * Gets the string representation of the current calculator screen.
	 *
	 * @return the string representation of the current calculator screen.
	 */
	@Override
	public String toString() {
		if (screen == null) {
			return "0";

		} else if (screen.endsWith(".0")) {
			return screen.substring(0, screen.lastIndexOf("."));

		} else {
			return screen == null ? "0" : String.valueOf(screen);
		}
	}

	/**
	 * Notifies observers.
	 */
	private void notifyObservers() {
		if (observers == null) {
			return;
		}

		for (CalcValueListener listener : observers) {
			listener.valueChanged(this);
		}
	}
}
