package hr.fer.zemris.java.hw05.db;

public class FieldValueGetters {
	
	public static final IFieldValueGetter LAST_NAME;
	public static final IFieldValueGetter FIRST_NAME;
	public static final IFieldValueGetter JMBAG;
	
	static {
		LAST_NAME = record -> record.getLastName();
		FIRST_NAME = record -> record.getFirstName();
		JMBAG = record -> record.getJmbag();
	}

}
