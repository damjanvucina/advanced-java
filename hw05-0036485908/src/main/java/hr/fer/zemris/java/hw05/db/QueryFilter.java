package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryFilter implements IFilter {

	List<ConditionalExpression> expressions;

	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

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
