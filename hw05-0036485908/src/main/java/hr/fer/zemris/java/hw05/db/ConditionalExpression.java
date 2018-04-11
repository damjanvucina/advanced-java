package hr.fer.zemris.java.hw05.db;

/**
 * The class that represents a conditional expression, i.e. a condition that a
 * specific element of this database can but does not have to fulfill.
 * 
 * @author Damjan Vučina
 */
public class ConditionalExpression {

	/**
	 * The field getter, i.e. the getter for the attribute identificator of an
	 * element this database.
	 */
	IFieldValueGetter fieldGetter;

	/**
	 * The string literal, i.e. the getter for the value of an attribute of an
	 * element of this database.
	 */
	String stringLiteral;

	/**
	 * The comparison operator, i.e. the operator that is identifier of an operation
	 * performed, e.g. greater than, equals etc.
	 */
	IComparisonOperator comparisonOperator;

	/**
	 * Instantiates a new conditional expression, i.e. a condition that a specific
	 * element of this database can but does not have to fulfill.
	 *
	 * @param fieldGetter
	 *            The field getter, i.e. the getter for the attribute identificator
	 *            of an element of this database.
	 * @param stringLiteral
	 *            The string literal, i.e. the getter for the value of an attribute of an element 
	 *            of this database.
	 * @param comparisonOperator
	 *            The comparison operator, i.e. the operator that is identifier of an
	 *            operation performed, e.g. greater than, equals etc.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {

		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Gets the field getter, i.e. attribute identificator  of an element of this database.
	 *
	 * @return the field getter, i.e. the getter for the attribute identificator  of an element of
	 *         this database.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Gets the string literal, i.e. value of an attribute of an element of this database.
	 *
	 * @return the string literal, i.e. value of an attribute of an element of this database.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Gets the comparison operator, i.e. the operator that is identifier of an
	 * operation performed, e.g. greater than, equals etc.
	 *
	 * @return the comparison operator, i.e. the operator that is identifier of an
	 *         operation performed, e.g. greater than, equals etc.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Checks if two instances of ConditionalExpression class are equal by
	 * calculating their hash. Two instances of TableEntry class are considered
	 * equal if they have identical key fieldGetter, stringLiteral and
	 * comparisonOperator attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comparisonOperator == null) ? 0 : comparisonOperator.hashCode());
		result = prime * result + ((fieldGetter == null) ? 0 : fieldGetter.hashCode());
		result = prime * result + ((stringLiteral == null) ? 0 : stringLiteral.hashCode());
		return result;
	}

	/**
	 * Checks if two instances of ConditionalExpression class are equal. Two
	 * instances of TableEntry class are considered equal if they have identical key
	 * fieldGetter, stringLiteral and comparisonOperator attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConditionalExpression other = (ConditionalExpression) obj;
		if (comparisonOperator == null) {
			if (other.comparisonOperator != null)
				return false;
		} else if (!comparisonOperator.equals(other.comparisonOperator))
			return false;
		if (fieldGetter == null) {
			if (other.fieldGetter != null)
				return false;
		} else if (!fieldGetter.equals(other.fieldGetter))
			return false;
		if (stringLiteral == null) {
			if (other.stringLiteral != null)
				return false;
		} else if (!stringLiteral.equals(other.stringLiteral))
			return false;
		return true;
	}

}
