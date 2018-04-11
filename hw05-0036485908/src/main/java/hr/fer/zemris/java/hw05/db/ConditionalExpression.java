package hr.fer.zemris.java.hw05.db;

public class ConditionalExpression {

	IFieldValueGetter fieldGetter;
	String stringLiteral;
	IComparisonOperator comparisonOperator;

	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {

		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comparisonOperator == null) ? 0 : comparisonOperator.hashCode());
		result = prime * result + ((fieldGetter == null) ? 0 : fieldGetter.hashCode());
		result = prime * result + ((stringLiteral == null) ? 0 : stringLiteral.hashCode());
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
