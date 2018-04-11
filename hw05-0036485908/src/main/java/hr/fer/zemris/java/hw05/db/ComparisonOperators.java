package hr.fer.zemris.java.hw05.db;

/**
 * The class that implements operations that can be performed over values of the elements of
 * this database.
 * 
 * @author Damjan Vučina
 */
public class ComparisonOperators {

	/** The Constant LESS. */
	public static final IComparisonOperator LESS;

	/** The Constant LESS_OR_EQUALS. */
	public static final IComparisonOperator LESS_OR_EQUALS;

	/** The Constant GREATER. */
	public static final IComparisonOperator GREATER;

	/** The Constant GREATER_OR_EQUALS. */
	public static final IComparisonOperator GREATER_OR_EQUALS;

	/** The Constant EQUALS. */
	public static final IComparisonOperator EQUALS;

	/** The Constant NOT_EQUALS. */
	public static final IComparisonOperator NOT_EQUALS;

	/** The Constant LIKE. */
	public static final IComparisonOperator LIKE;

	static {
		LESS = (value1, value2) -> value1.compareTo(value2) < 0;
		LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
		GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
		GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
		EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
		NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;

		LIKE = new IComparisonOperator() {

			@Override
			public boolean satisfied(String value1, String value2) {

				if (value2.contains("*")) {
					if (value2.indexOf("*") != value2.lastIndexOf("*")) {
						StudentDatabase.illegalStudentDatabaseState("Pattern can contain a single * character.");

					} else {
						if (value2.startsWith("*")) {
							value2 = value2.substring(1);
							value1 = value1.substring(value1.length() - value2.length());

						} else if (value2.endsWith("*")) {
							value1 = value1.substring(0, value2.indexOf("*"));
							value2 = value2.substring(0, value2.indexOf("*"));

						} else {// true if value1 starts with (everything preceding * in value2) and ends with
								// (everything following * in value2)
							return value1.startsWith(value2.substring(0, value2.indexOf("*")))
									&& value1.endsWith(value2.substring(value2.indexOf("*") + 1));
						}

					}

				}

				return value1.equals(value2);

			}
		};
	}

}
