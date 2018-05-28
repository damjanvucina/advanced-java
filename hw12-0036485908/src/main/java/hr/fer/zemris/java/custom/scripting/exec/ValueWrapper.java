package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * The Class ValueWrapper that all values to be stored to ObjectMultistack class
 * are encapsulated into. Values i.e. instances of ValueWrapper class are prior
 * to storing encapsulated in an instance of MultistackEntry class. This policy
 * enables ObjectMultistack class to provide user with standard methods for
 * working with stacks and perform operations such as push, peek or pop in O(1)
 * complexity. This class provides user with methods for performing arithmetic
 * operation over instances of ValueWrapper class. Supported mathematical
 * operations are addition, subtraction, multiplication and division. This class
 * also provides user with method for checking whether two Objects are
 * numerically equal.
 * 
 * @author Damjan Vučina
 */
public class ValueWrapper {

	/** The Constant INTEGER_ADDITION used for adding integer values. */
	public static final BiFunction<Integer, Integer, Object> INTEGER_ADDITION = (v1, v2) -> v1 + v2;

	/** The Constant INTEGER_SUBTRACTION used for subtracting integer values.. */
	public static final BiFunction<Integer, Integer, Object> INTEGER_SUBTRACTION = (v1, v2) -> v1 - v2;

	/** The Constant INTEGER_MULTIPLICATION used for multiplying integer values.. */
	public static final BiFunction<Integer, Integer, Object> INTEGER_MULTIPLICATION = (v1, v2) -> v1 * v2;

	/** The Constant INTEGER_DIVISION used for dividing integer values.. */
	public static final BiFunction<Integer, Integer, Object> INTEGER_DIVISION = (v1, v2) -> v1 / v2;

	/** The Constant DOUBLE_ADDITION used for adding double values.. */
	public static final BiFunction<Double, Double, Object> DOUBLE_ADDITION = (v1, v2) -> v1 + v2;

	/** The Constant DOUBLE_SUBTRACTION used for subtracting double values.. */
	public static final BiFunction<Double, Double, Object> DOUBLE_SUBTRACTION = (v1, v2) -> v1 - v2;

	/** The Constant DOUBLE_MULTIPLICATION used for multiplying double values.. */
	public static final BiFunction<Double, Double, Object> DOUBLE_MULTIPLICATION = (v1, v2) -> v1 * v2;

	/** The Constant DOUBLE_DIVISION used for dividing double values.. */
	public static final BiFunction<Double, Double, Object> DOUBLE_DIVISION = (v1, v2) -> v1 / v2;

	/** The value of the element. */
	private Object value;

	/**
	 * Instantiates a new value wrapper.
	 *
	 * @param value
	 *            the value of the element
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Gets the value of the stored ValueWrapper class.
	 *
	 * @return the value of the stored ValueWrapper class.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value of the stored ValueWrapper class..
	 *
	 * @param value
	 *            the new value of the stored ValueWrapper class.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Performs addition over stored ValueWrapper class and provided object argument
	 * and stores the result back to the value.
	 *
	 * @param incValue
	 *            the value to be added to the value of the stored ValueWrapper
	 */
	public void add(Object incValue) {
		value = performOperation(value, incValue, DOUBLE_ADDITION, INTEGER_ADDITION);
	}

	/**
	 * Performs subtraction over stored ValueWrapper class and provided object
	 * argument and stores the result back to the value.
	 *
	 * @param decValue
	 *            the value to be subtracted from the value of the stored
	 *            ValueWrapper
	 */
	public void subtract(Object decValue) {
		value = performOperation(value, decValue, DOUBLE_SUBTRACTION, INTEGER_SUBTRACTION);
	}

	/**
	 * Performs multiplication over stored ValueWrapper class and provided object
	 * argument and stores the result back to the value.
	 *
	 * @param mulValue
	 *            the value to be multiplied by the value of the stored ValueWrapper
	 */
	public void multiply(Object mulValue) {
		value = performOperation(value, mulValue, DOUBLE_MULTIPLICATION, INTEGER_MULTIPLICATION);
	}

	/**
	 * Performs division over stored ValueWrapper class and provided object argument
	 * and stores the result back to the value.
	 *
	 * @param divValue
	 *            the value stored ValueWrapper is to be divided by.
	 */
	public void divide(Object divValue) {
		checkForDivisionByZero(divValue);
		value = performOperation(value, divValue, DOUBLE_DIVISION, INTEGER_DIVISION);
	}

	/**
	 * Checks if division by zero occured.
	 *
	 * @param value
	 *            the value to be checked
	 * @throws ObjectMultiStackException
	 *             if division by zero occured.
	 */
	private void checkForDivisionByZero(Object value) {
		if (value == null) {
			throw new ObjectMultistackException("Cannot divide by zero.");
		}

		if (value instanceof Number) {
			if (((Number) value).intValue() == 0) {
				throw new ObjectMultistackException("Cannot divide by zero.");
			}
		}
	}

	/**
	 * Performs the specified arithmetic operation over elements provided via
	 * arguments.
	 *
	 * @param value
	 *            the value of the ValueWrapper
	 * @param argument
	 *            the value of the argument
	 * @param doubleAction
	 *            the Function used for performing arithmetic operations over
	 *            doubles
	 * @param integerAction
	 *            the Function used for performing arithmetic operations over
	 *            integers
	 * @return the object representation of the result of the arithmetic operation
	 * @throws ObjectMultistackException
	 *             if type of the argument is nut supported. Supported types are
	 *             null, Integer, Double and String representation of both Double
	 *             and Integer
	 */
	//@formatter:off
	public static Object performOperation(Object value, Object argument,
										  BiFunction<Double, Double, Object> doubleAction,
										  BiFunction<Integer, Integer, Object> integerAction) {
		
		Object firstFactor = identifyOperandType(value);
		Object secondFactor = identifyOperandType(argument);

		if (firstFactor instanceof Double || secondFactor instanceof Double) {
			return doubleAction.apply(((Number) firstFactor).doubleValue(), ((Number) secondFactor).doubleValue());
		}
	
		else {
			return integerAction.apply((Integer) firstFactor, (Integer) secondFactor);
		}
	}
	//@formatter:on

	/**
	 * Identify operand type.
	 *
	 * @param argument
	 *            the argument
	 * @return the object
	 * @throws ObjectMultistackException
	 *             if type of the argument is nut supported or if argument type is
	 *             String but neither parsable to Double nor to Integer. Supported
	 *             types are null, Integer, Double and String representation of both
	 *             Double and Integer.
	 */
	private static Object identifyOperandType(Object argument) {
		if (argument == null) {
			return Integer.valueOf(0);

		} else if (argument instanceof Integer) {
			return (Integer) argument;

		} else if (argument instanceof Double) {
			return (Double) argument;

		} else if (argument instanceof String) {
			return checkIfParsable((String) argument);
		}

		throw new ObjectMultistackException("Unsupported argument type, was: " + argument.getClass());
	}

	/**
	 * Check if parsable.
	 *
	 * @param argument
	 *            the argument
	 * @return the object
	 * @throws ObjectMultistackException
	 *             if Argument type is neither parsable to Double nor to Integer
	 */
	private static Object checkIfParsable(String argument) {
		try {
			return Integer.parseInt(argument);

		} catch (NumberFormatException e) {
		}

		try {
			return Double.parseDouble(argument);

		} catch (NumberFormatException e) {
		}

		throw new ObjectMultistackException("Argument type is neither parsable to Double nor to Integer");
	}

	/**
	 * Method for checking whether two Objects are numerically equal..
	 *
	 * @param withValue
	 *            second operand
	 * @return a number < 0 if this value is lesser, a number > 0 if this number is
	 *         bigger and 0 if this value is equal to the one provided via argument
	 */
	public int numCompare(Object withValue) {
		Object firstFactor = identifyOperandType(value);
		Object secondFactor = identifyOperandType(withValue);

		return Double.compare(((Number) firstFactor).doubleValue(), ((Number) secondFactor).doubleValue());
	}

	/**
	 * Return a String representation of this ValueWrapper
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Checks if two instances of ValueWrapper class are equal by calculating their
	 * hash. Two instances of ValueWrapper class are considered equal if they have
	 * identical value attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * Checks if two instances of ValueWrapper class are equal. Two instances of
	 * ValueWrapper class are considered equal if they have identical key
	 * attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueWrapper other = (ValueWrapper) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * The main method invoked when the program is run.
	 *
	 * @param args
	 *            the arguments. Notice: not used here
	 */
	public static void main(String[] args) {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		System.out.println("v1 now stores Integer(0); v2 still stores null");
		System.out.println("v1 = " + v1.getValue() + ", class: " + v1.getValue().getClass().getSimpleName());
		System.out.println("v2 = " + v2);
		System.out.println();

		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());

		System.out.println("v3 now stores Double(13); v4 still stores Integer(1)");
		System.out.println("v3 = " + v3.getValue() + ", class: " + v3.getValue().getClass().getSimpleName());
		System.out.println("v4 = " + v4.getValue() + ", class: " + v4.getValue().getClass().getSimpleName());
		System.out.println();

		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());

		System.out.println("v5 now stores Integer(13); v6 still stores Integer(1)");
		System.out.println("v5 = " + v5.getValue() + ", class: " + v5.getValue().getClass().getSimpleName());
		System.out.println("v6 = " + v6.getValue() + ", class: " + v6.getValue().getClass().getSimpleName());
		System.out.println();

		try {
			ValueWrapper v7 = new ValueWrapper("Ankica");
			ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
			v7.add(v8.getValue()); // throws RuntimeException

		} catch (ObjectMultistackException e) {
			System.out.println(e.getMessage());
		}

	}
}
