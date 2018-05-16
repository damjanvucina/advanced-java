package hr.fer.zemris.java.gui.calc;

/**
 * Observer pattern is in action when it comes to this calculator
 * implementation. This interface defines method that is to be invoked upon
 * instance of the class representing the observer in Observer pattern notifying
 * it the value has been changed. 
 *
 */
public interface CalcValueListener {

	/**
	 * Value changed.
	 *
	 * @param model
	 *            the model
	 */
	void valueChanged(CalcModel model);
}