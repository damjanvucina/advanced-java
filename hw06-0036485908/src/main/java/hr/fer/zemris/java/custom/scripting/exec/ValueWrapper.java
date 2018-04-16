package hr.fer.zemris.java.custom.scripting.exec;

public class ValueWrapper {

	public static final String NULL = "null";
	public static final String DOUBLE = "Double";
	public static final String INTEGER = "Integer";

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
		String argumentType = identifyArgumentType(incValue);
		String valueType = identifyArgumentType(value);

		if (value instanceof Double && incValue instanceof Double) {
			value = (Double) value + (Double) incValue;

		} else if (value instanceof Double) {
			value = (incValue == null) ? (Double) value + Integer.valueOf(0) : (Double) value + (Double) incValue;

		} else if (incValue instanceof Double) {
			value = (value == null) ? Integer.valueOf(0) + (Double) incValue : (Double) value + (Double) incValue;
		
		} else {

		}
	}

	private String identifyArgumentType(Object argument) {
		if (argument == null) {
			return NULL;

		} else if (argument instanceof Integer) {
			return INTEGER;

		} else if (argument instanceof Double) {
			return DOUBLE;

		} else if (argument instanceof String) {
			return checkIfParsable((String) argument);
		}

		throw new ObjectMultistackException("Unsupported argument type, was: " + argument.getClass());
	}

	private String checkIfParsable(String argument) {
		try {
			Double.parseDouble(argument);
			return DOUBLE;

		} catch (NumberFormatException e) {
		}

		try {
			Integer.parseInt(argument);
			return INTEGER;

		} catch (NumberFormatException e) {
		}

		throw new ObjectMultistackException(
				"Argument type is neither parsable to Double nor to Integer, was: " + argument.getClass());
	}

	public void subtract(Object decValue) {

	}

	public void multiply(Object mulValue) {

	}

	public void divide(Object divValue) {

	}

	public int numCompare(Object withValue) {
		return -1;
	}

}
