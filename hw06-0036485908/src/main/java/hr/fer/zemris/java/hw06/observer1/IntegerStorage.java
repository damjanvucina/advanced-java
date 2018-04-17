package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class which is responsible for storing the observed number. This class is
 * a part of "Observer pattern", software design pattern in which an object,
 * called the Subject, maintains a list of its dependents, called Observers, and
 * notifies them automatically of any state changes, usually by calling one of
 * their methods, method valueChanged in this case. This class implements
 * IntegerStorageObserver interface which prescribes the designated contract for
 * communication between Subject (this class) and its Concrete observers. This
 * class provides user with methods for registering and deregistering Concrete
 * observers and for altering the stored value. Whenever the stored value is
 * changed, this class notifies all currently registered observers.
 * 
 * @author Damjan Vučina
 */
public class IntegerStorage {

	/**
	 * The value which is observed in this Observer pattern. For further information
	 * about Observer pattern make sure to look up class level documentation.
	 */
	private int value;

	/**
	 * The list of currently registered concrete observers. For further information
	 * about concrete observers make sure to look up class level documentation.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Instantiates a new Integer storage class
	 *
	 * @param initialValue
	 *            The value which is observed in this Observer pattern. For further
	 *            information about Observer pattern make sure to look up class
	 *            level documentation.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Registers the given Concrete observer. For further information about concrete
	 * observers make sure to look up class level documentation.
	 *
	 * @param observer
	 *            the to be registered Concrete observer. For further information
	 *            about concrete observers make sure to look up class level
	 *            documentation.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Deregisters the given Concrete observer. For further information about
	 * concrete observers make sure to look up class level documentation.
	 *
	 * @param observer
	 *            the to be deregistered Concrete observer. For further information
	 *            about concrete observers make sure to look up class level
	 *            documentation.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Deregisters all Concrete observers. For further information about concrete
	 * observers make sure to look up class level documentation.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Gets the currently stored value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value if it is not equal to the currently stored one.
	 *
	 * @param value
	 *            the new value to be stored to this IntegerStorage
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(this);
				}
			}
		}

	}
}
