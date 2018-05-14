package hr.fer.zemris.java.gui.calc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
	public static final String STARTING_ZERO = "0";
	public static final int MAX_DIGITS = 308;

	private String screen;
	private String activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> observers;
	DecimalFormat decimalFormat;

	public CalcModelImpl() {
		observers = new ArrayList<>();
		decimalFormat = new DecimalFormat("#.##########");
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (observers.contains(l)) {
			throw new CalculatorException("Specifed observer is already registered.");
		}

		observers.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (!observers.contains(l)) {
			throw new CalculatorException("Specifed observer is not registered.");
		}

		observers.remove(l);
	}

	@Override
	public double getValue() {
		return screen == null ? 0 : Double.parseDouble(screen);
	}

	@Override
	public void setValue(double value) {
		validateValue(value);

		//screen = String.valueOf(value);
		screen = decimalFormat.format(value);
		notifyObservers();
	}

	private void validateValue(double value) {
		if (Double.isNaN(value)) {
			throw new CalculatorException("Value cannot be NaN.");
		}
		if (Double.isInfinite(value)) {
			throw new CalculatorException("Value cannot be infinity.");
		}
	}

	@Override
	public void clear() {
		screen = null;
		notifyObservers();
	}
	
	public void clearWithoutNotifying() {
		screen = null;
	}

	@Override
	public void clearAll() {
		activeOperand = null;
		pendingOperation = null;
		clear();
	}
	
	public void clearAllWithoutNotifying() {
		activeOperand = null;
		pendingOperation = null;
		clearWithoutNotifying();
	}

	@Override
	public void swapSign() {
		if (screen == null) {
			return;
		}

		screen = String.valueOf(Double.parseDouble(screen) * -1);
		notifyObservers();
	}

	@Override
	public void insertDecimalPoint() {
		if (screen != null && screen.contains(".")) {
			return;
		}
		screen = (screen == null) ? "0" + String.valueOf(".") : screen + String.valueOf(".");
		notifyObservers();
	}

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

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set yet.");
		}

		return Double.parseDouble(activeOperand);
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		validateValue(activeOperand);

		this.activeOperand = String.valueOf(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}
	
	public void clearPendingOperation() {
		pendingOperation = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if (op == null) {
			throw new CalculatorException("Pending operation cannot be set to null.");
		}

		pendingOperation = op;
	}

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

	private void notifyObservers() {
		if (observers == null) {
			return;
		}

		for (CalcValueListener listener : observers) {
			listener.valueChanged(this);
		}
	}
}