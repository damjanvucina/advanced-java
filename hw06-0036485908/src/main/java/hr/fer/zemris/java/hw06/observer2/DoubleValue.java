package hr.fer.zemris.java.hw06.observer2;

public class DoubleValue implements IntegerStorageObserver {

	int cyclesRemaining;

	public DoubleValue(int cyclesRemaining) {
		if (cyclesRemaining < 1) {
			throw new IllegalArgumentException("Number of remaining cycles cannot be set to a value less than one.");
		}

		this.cyclesRemaining = cyclesRemaining;
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Double value: " + change.getPostChangeValue() * 2);
		cyclesRemaining--;

		validateRemainingCycles(change.getIstorage());

	}

	public void validateRemainingCycles(IntegerStorage istorage) {
		if (cyclesRemaining <= 0) {
			istorage.removeObserver(this);
		}
	}

}
