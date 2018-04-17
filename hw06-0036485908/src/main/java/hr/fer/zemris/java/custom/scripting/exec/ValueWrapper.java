package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

public class ValueWrapper {

	public static final BiFunction<Integer, Integer, Object> INTEGER_ADDITION = (v1, v2) -> v1 + v2;
	public static final BiFunction<Integer, Integer, Object> INTEGER_SUBTRACTION = (v1, v2) -> v1 - v2;
	public static final BiFunction<Integer, Integer, Object> INTEGER_MULTIPLICATION = (v1, v2) -> v1 * v2;
	public static final BiFunction<Integer, Integer, Object> INTEGER_DIVISION = (v1, v2) -> v1 / v2;
	
	public static final BiFunction<Double, Double, Object> DOUBLE_ADDITION = (v1, v2) -> v1 + v2;
	public static final BiFunction<Double, Double, Object> DOUBLE_SUBTRACTION = (v1, v2) -> v1 - v2;
	public static final BiFunction<Double, Double, Object> DOUBLE_MULTIPLICATION = (v1, v2) -> v1 * v2;
	public static final BiFunction<Double, Double, Object> DOUBLE_DIVISION = (v1, v2) -> v1 / v2;	

	private Object value;

	public ValueWrapper(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void add(Object incValue) {
		value = performOperation(value, incValue, DOUBLE_ADDITION, INTEGER_ADDITION);
	}

	public void subtract(Object decValue) {
		value = performOperation(value, decValue, DOUBLE_SUBTRACTION, INTEGER_SUBTRACTION);
	}

	public void multiply(Object mulValue) {
		value = performOperation(value, mulValue, DOUBLE_MULTIPLICATION, INTEGER_MULTIPLICATION);
	}

	public void divide(Object divValue) {
		checkForDivisionByZero(divValue);
		value = performOperation(value, divValue, DOUBLE_DIVISION, INTEGER_DIVISION);
	}

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

	public int numCompare(Object withValue) {
		Object firstFactor = identifyOperandType(value);
		Object secondFactor = identifyOperandType(withValue);

		return Double.compare(((Number) firstFactor).doubleValue(), ((Number) secondFactor).doubleValue());
	}

	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

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
