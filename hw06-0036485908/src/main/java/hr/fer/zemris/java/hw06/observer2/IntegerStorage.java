package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers;
	private IntegerStorageChange change;

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	public void clearObservers() {
		observers.clear();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (this.value != value) {
			int oldValue = this.value;
			this.value = value;
			
			if (observers != null) {
				change = new IntegerStorageChange(this, oldValue, value);
				
				for (IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(change);
				}
			}
		}

	}
}
