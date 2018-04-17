package hr.fer.zemris.java.hw06.observer1;

/**
 * The Class which is responsible for monitoring the value stored in
 * IntegerStorage class. Whenever that value is changed, valueChanged method of
 * this class is invoked and it prints the double of that new value to the
 * console. When instantiated, instance of this class receives a number which
 * represents remaining life cycles, i.e. the number of times it will print
 * double value to the console before it automatically deregisters itself. This
 * class is a part of "Observer pattern", software design pattern in which an
 * object, called the Subject, maintains a list of its dependents, called
 * Observers, and notifies them automatically of any state changes, usually by
 * calling one of their methods, method valueChanged in this case. This class
 * implements IntegerStorageObserver interface which prescribes the designated
 * contract for communication between Subject (IntegerStorage class) and its
 * Concrete observers(this class being one of them).
 * 
 * @author Damjan Vučina
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * The cycles remaining, i.e. the number of times this class will print double
	 * value of number stored in IntegerStorage class to the console before it
	 * automatically deregisters itself.
	 */
	private int cyclesRemaining;

	/**
	 * Instantiates a new DoubleValue Concrete observer. For further information
	 * about concrete observers make sure to look up class level documentation.
	 *
	 * @param cyclesRemaining
	 *            the cycles remaining, i.e. the number of times this class will
	 *            print double value of number stored in IntegerStorage class to the
	 *            console before it automatically deregisters itself.
	 */
	public DoubleValue(int cyclesRemaining) {
		if (cyclesRemaining < 1) {
			throw new IllegalArgumentException("Number of remaining cycles cannot be set to a value less than one.");
		}

		this.cyclesRemaining = cyclesRemaining;
	}

	/**
	 * Method invoked by IntegerStorage class whenever the stored value in that
	 * class is changed. When invoked, this method prints the double of that newly
	 * stored value in IntegerStorage class to the console.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue() * 2);
		cyclesRemaining--;

		validateRemainingCycles(istorage);

	}

	/**
	 * Validates if this class has any remaining cycles.When instantiated, instance
	 * of this class receives a number which represents remaining life cycles, i.e.
	 * the number of times it will print double value to the console before it
	 * automatically deregisters itself. If there are none, this method initiates
	 * the process of deregistering this class from designated IntegerStorage class.
	 *
	 * @param istorage
	 *            the istorage
	 */
	public void validateRemainingCycles(IntegerStorage istorage) {
		if (cyclesRemaining <= 0) {
			istorage.removeObserver(this);
		}
	}

}
