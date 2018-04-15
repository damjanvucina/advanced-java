package hr.fer.zemris.java.hw06.observer2;

import static java.lang.Math.pow;

public class SquareValue implements IntegerStorageObserver {

	public SquareValue() {
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		int value = change.getPostChangeValue();
		System.out.format("Provided new value: %d, square is %.0f%n", value, pow(value, 2));
	}

}
