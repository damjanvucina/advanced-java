package hr.fer.zemris.java.hw06.observer2;

public class ChangeCounter implements IntegerStorageObserver {

	private int numOfChanges;

	public ChangeCounter() {
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		numOfChanges++;
		System.out.println("Number of value changes since tracking: " + numOfChanges);
	}

}
