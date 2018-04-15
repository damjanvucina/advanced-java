package hr.fer.zemris.java.hw06.observer2;

public class IntegerStorageChange {

	private IntegerStorage istorage;
	private int preChangeValue;
	private int postChangeValue;

	public IntegerStorageChange(IntegerStorage istorage, int preChangeValue, int postChangeValue) {
		this.istorage = istorage;
		this.preChangeValue = preChangeValue;
		this.postChangeValue = postChangeValue;
	}

	public IntegerStorage getIstorage() {
		return istorage;
	}

	public int getPreChangeValue() {
		return preChangeValue;
	}

	public int getPostChangeValue() {
		return postChangeValue;
	}

}
