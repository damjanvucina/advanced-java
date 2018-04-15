package hr.fer.zemris.java.hw06.observer1;

import static java.lang.Math.pow;

public class SquareValue implements IntegerStorageObserver {

	public SquareValue() {
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.format("Provided new value: %d, square is %.0f%n", value, pow(value, 2));
	}

}
