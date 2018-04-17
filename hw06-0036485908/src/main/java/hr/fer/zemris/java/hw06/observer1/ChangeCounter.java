package hr.fer.zemris.java.hw06.observer1;

/**
 * * The Class which is responsible for monitoring the value stored in
 * IntegerStorage class. Whenever that value is changed, valueChanged method of
 * this class is invoked and it prints the total number of changes made to that
 * value since this class was registered, i.e. since the tracking had began.
 * This class monitors the changes made to the value stored in IntegerStorage
 * class until deregistered from it. This class is a part of "Observer pattern",
 * software design pattern in which an object, called the Subject, maintains a
 * list of its dependents, called Observers, and notifies them automatically of
 * any state changes, usually by calling one of their methods, method
 * valueChanged in this case. This class implements IntegerStorageObserver
 * interface which prescribes the designated contract for communication between
 * Subject (IntegerStorage class) and its Concrete observers(this class being
 * one of them).
 * 
 * @author Damjan Vučina
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * The total number of changes made to that value since this class was
	 * registered, i.e. since the tracking had began.
	 */
	private int numOfChanges;

	/**
	 * * Instantiates a new ChangeCounter Concrete observer. For further information
	 * about concrete observers make sure to look up class level documentation.
	 */
	public ChangeCounter() {
	}

	/**
	 * Method invoked by IntegerStorage class whenever the stored value in that
	 * class is changed. When invoked, this method prints the total number of
	 * changes made to that value since this class was registered, i.e. since the
	 * tracking had began.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		numOfChanges++;
		System.out.println("Number of value changes since tracking: " + numOfChanges);
	}

}
