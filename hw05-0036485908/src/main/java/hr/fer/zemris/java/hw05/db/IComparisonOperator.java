package hr.fer.zemris.java.hw05.db;

/**
 * The Interface IComparisonOperator that defines a method performed over
 * comparison operator used for determining the relations between the elements
 * in the arguments.
 * 
 * @author Damjan Vučina
 */
public interface IComparisonOperator {

	/**
	 * Method performed over comparison operator used for determining the relations
	 * between the elements in the arguments.
	 *
	 * @param value1
	 *            the first element
	 * @param value2
	 *            the second element
	 * @return true, if the relation specified in arguments is true
	 */
	boolean satisfied(String value1, String value2);
}
