package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * The interface defining the methods used for the interaction between the user
 * and the calculator. Observer pattern is used and in this implementation,
 * calculator's screen is observer while the calculator itself is subject.
 * 
 */
public interface CalcModel {

	/**
	 * Adds the calculator value listener.
	 *
	 * @param l
	 *            the calculator value listener.
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Removes the calculator value listener.
	 *
	 * @param l
	 *            the calculator value listener.
	 */
	void removeCalcValueListener(CalcValueListener l);

	/**
	 * Gets the string representation of the current calculator screen.
	 *
	 * @return the string representation of the current calculator screen.
	 */
	String toString();

	/**
	 * Gets the current value.
	 *
	 * @return the current value.
	 */
	double getValue();

	/**
	 * Sets the new value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(double value);

	/**
	 * Clears current entry.
	 */
	void clear();

	/**
	 * Clears current entry and cache by restarting the calculator.
	 */
	void clearAll();

	/**
	 * Swaps current sign.
	 */
	void swapSign();

	/**
	 * Inserts decimal point.
	 */
	void insertDecimalPoint();

	/**
	 * Inserts digit.
	 *
	 * @param digit
	 *            the digit
	 */
	void insertDigit(int digit);

	/**
	 * Checks if is active operand is set.
	 *
	 * @return true, if is active operand set
	 */
	boolean isActiveOperandSet();

	/**
	 * Gets the active operand.
	 *
	 * @return the active operand
	 */
	double getActiveOperand();

	/**
	 * Sets the active operand.
	 *
	 * @param activeOperand
	 *            the new active operand
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Clears active operand.
	 */
	void clearActiveOperand();

	/**
	 * Gets the pending binary operation.
	 *
	 * @return the pending binary operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Sets the pending binary operation.
	 *
	 * @param op
	 *            the new pending binary operation
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}