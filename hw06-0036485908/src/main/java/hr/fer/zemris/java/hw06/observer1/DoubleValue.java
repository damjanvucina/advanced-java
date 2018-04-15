package hr.fer.zemris.java.hw06.observer1;

public class DoubleValue implements IntegerStorageObserver {

	int cyclesRemaining;

	public DoubleValue(int cyclesRemaining) {
		if (cyclesRemaining < 1) {
			throw new IllegalArgumentException("Number of remaining cycles cannot be set to a value less than one.");
		}

		this.cyclesRemaining = cyclesRemaining;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue() * 2);
		cyclesRemaining--;
		
		validateRemainingCycles(istorage);

	}

	public void validateRemainingCycles(IntegerStorage istorage) {
		if (cyclesRemaining <= 0) {
			istorage.removeObserver(this);
		}
	}

}
