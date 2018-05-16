package hr.fer.zemris.java.gui.prim;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The class used for creating a window containing two least each of them
 * printing a next prime number on a click of the designated button.
 * 
 * @author Damjan Vučina
 */
public class PrimListModel implements ListModel<Integer> {

	/** The Constant START. */
	private static final int START = 1;

	/** The list of processed primes. */
	private List<Integer> primes = new ArrayList<>();

	/** The registered listeners. Observer pattern is in action. */
	private List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * Instantiates a new prim list model.
	 */
	public PrimListModel() {
		next();
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the data
	 * model occurs.
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.add(l);
	}

	/**
	 * Returns the value at the specified index.
	 */
	@Override
	public Integer getElementAt(int index) {
		if (index < 0 || index > getSize() - 1) {
			throw new IllegalArgumentException("Invalid index, was:" + index);
		}
		return primes.get(index);
	}

	/**
	 * Returns the length of the list.
	 */
	@Override
	public int getSize() {
		return primes.size();
	}

	/**
	 * Removes a listener from the list that's notified each time a change to the
	 * data model occurs.
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");

		listeners.remove(l);
	}

	/**
	 * Acquires the next prime number and notifies observers.
	 */
	public void next() {
		int position = primes.size();
		primes.add(position == 0 ? START : generateNextPrime());

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Generates next prime number.
	 *
	 * @return the next prime number
	 */
	private int generateNextPrime() {
		int lastPrime = primes.get(primes.size() - 1);

		for (int current = lastPrime + 1;; current++) {
			if (isPrime(current)) {
				return current;
			}
		}

	}

	/**
	 * Checks if the number is prime.
	 *
	 * @param number
	 *            the number
	 * @return true, if is prime
	 */
	public boolean isPrime(int number) {
		if (number < 1) {
			throw new IllegalArgumentException("Number cannot be lesser than one.");
		}

		if (number != 2 && number % 2 == 0) {
			return false;
		}

		for (int i = 3, root = (int) sqrt(number); i <= root; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

}
