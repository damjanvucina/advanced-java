package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * The class that is used to perform validation checks over elements of this
 * database.
 * 
 * @author Damjan Vučina
 */
public class QueryFilter implements IFilter {

	/**
	 * The list of expressions recognized when parsing the text. A conditional
	 * expression is a condition that a specific element of this database can but
	 * does not have to fulfill.
	 */
	List<ConditionalExpression> expressions;

	/**
	 * Instantiates a new query filter.
	 *
	 * @param expressions
	 *            The list of expressions recognized when parsing the text. A
	 *            conditional expression is a condition that a specific element of
	 *            this database can but does not have to fulfill.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	/**
	 * Method for checking if the performed query is true, by validating conditional
	 * expression constructed from that query.A conditional expression is a
	 * condition that a specific element of this database can but does not have to
	 * fulfill.
	 *
	 * @param record
	 *            the given record
	 * @return true, if the performed query is true.
	 */
	@Override
	public boolean accepts(StudentRecord record) {

		for (ConditionalExpression expression : expressions) {
			if (!(expression.getComparisonOperator().satisfied(record.getAttribute(expression.getFieldGetter()),
					expression.getStringLiteral()))) {
				return false;
			}
		}

		return true;
	}

}
