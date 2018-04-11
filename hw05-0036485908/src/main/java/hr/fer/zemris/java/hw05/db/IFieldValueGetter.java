package hr.fer.zemris.java.hw05.db;

/**
 * The Interface IFieldValueGetter which is responsible for obtaining a
 * requested field value from given StudentRecord.
 * 
 * @author Damjan Vučina
 */
public interface IFieldValueGetter {

	/**
	 * Gets the requested field value from given StudentRecord..
	 *
	 * @param record
	 *            the given record
	 * @return the requested field
	 */
	String get(StudentRecord record);
}
