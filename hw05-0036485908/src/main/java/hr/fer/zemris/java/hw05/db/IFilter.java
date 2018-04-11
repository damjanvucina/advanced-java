package hr.fer.zemris.java.hw05.db;

/**
 * The Interface IFilter that defines a method for checking if the performed
 * query is true.
 * 
 * @author Damjan Vučina
 */
public interface IFilter {

	/**
	 * Method for checking if the performed query is true.
	 *
	 * @param record
	 *            the given record
	 * @return true, if the performed query is true.
	 */
	boolean accepts(StudentRecord record);
}
