package hr.fer.zemris.java.hw06.observer2;

/**
 * The class that is used for encapsulating information about the change in the
 * stored value in IntegerStorage class. It encapsulates reference to designated
 * IntegerStorage class as well as a copy of both old and new value.
 */
public class IntegerStorageChange {

	/**
	 * Reference to designated IntegerStorage class that stores the observed value.
	 */
	private IntegerStorage istorage;

	/** The pre-change value. */
	private int preChangeValue;

	/** The post-change value. */
	private int postChangeValue;

	/**
	 * Instantiates a new IntegerStorageChange class
	 *
	 * @param istorage
	 *            Reference to designated IntegerStorage class that stores the
	 *            observed value.
	 * @param preChangeValue
	 *            the pre-change value
	 * @param postChangeValue
	 *            the post-change value
	 */
	public IntegerStorageChange(IntegerStorage istorage, int preChangeValue, int postChangeValue) {
		this.istorage = istorage;
		this.preChangeValue = preChangeValue;
		this.postChangeValue = postChangeValue;
	}

	/**
	 * Gets the designated IntegerStorage class that stores the observed value.
	 *
	 * @return the designated IntegerStorage class that stores the observed value.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Gets the pre-change value.
	 *
	 * @return the pre-change value
	 */
	public int getPreChangeValue() {
		return preChangeValue;
	}

	/**
	 * Gets the post-change value.
	 *
	 * @return the post-change value
	 */
	public int getPostChangeValue() {
		return postChangeValue;
	}

}
