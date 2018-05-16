package hr.fer.zemris.java.gui.prim;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {
	private static final int START = 1;
	
	private List<Integer> primes = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();

	public PrimListModel() {
		next();
	}
	@Override
	public void addListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");
		
		listeners.add(l);
	}

	@Override
	public Integer getElementAt(int index) {
		if(index < 0 || index > getSize() -1) {
			throw new IllegalArgumentException("Invalid index, was:" + index);
		}
		return primes.get(index);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		Objects.requireNonNull(l, "Listener cannot be null");
		
		listeners.remove(l);
	}

	public void next() {
		int position = primes.size();
		primes.add(position == 0 ? START : generateNextPrime());
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	private int generateNextPrime() {
		int lastPrime = primes.get(primes.size() - 1);
		
		for (int current = lastPrime + 1;; current++) {
			if (isPrime(current)) {
				return current;
			}
		}

	}
	
	public boolean isPrime(int number) {
		if(number < 1) {
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
