package hr.fer.zemris.java.hw05.db;

/**
 * The class that represents a field getter, i.e. attribute identificator of an
 * element of this database.
 * 
 * @author Damjan Vučina
 */
public class FieldValueGetters {

	/** The Constant LAST_NAME that represents the last name of a student of this database. */
	public static final IFieldValueGetter LAST_NAME;

	/** The Constant FIRST_NAME that represents the first name of a student of this database. */
	public static final IFieldValueGetter FIRST_NAME;

	/** The Constant JMBAG that represents jmbag identification number of a student of this database. */
	public static final IFieldValueGetter JMBAG;

	static {
		LAST_NAME = record -> record.getLastName();
		FIRST_NAME = record -> record.getFirstName();
		JMBAG = record -> record.getJmbag();
	}

}
