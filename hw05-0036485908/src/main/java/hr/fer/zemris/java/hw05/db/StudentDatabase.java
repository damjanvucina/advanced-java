package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * The class that represents a database of students and initializes by reading
 * from .txt file. It contains an index of all StudentRecords which are indexed
 * by their jmbag for faster acquisition.
 * 
 * @author Damjan Vučina
 */
public class StudentDatabase {

	/** The lines of the input file. */
	private List<String> lines;

	/** The list of student records from the input file. */
	private List<StudentRecord> studentRecords;

	/**
	 * The index of all StudentRecords which are indexed by their jmbag for faster
	 * acquisition..
	 */
	private SimpleHashtable<String, StudentRecord> index;

	/**
	 * Instantiates a new student database.
	 *
	 * @param lines
	 *            The lines of the input file.
	 */
	public StudentDatabase(List<String> lines) {
		this.lines = lines;

		studentRecords = new LinkedList<>();
		index = new SimpleHashtable<>(lines.size());

		createRecordsAndIndexes();
	}

	/**
	 * Gets the list of student records.
	 *
	 * @return the list of student records.
	 */
	public List<StudentRecord> getStudentRecords() {
		return studentRecords;
	}

	/**
	 * Creates the list of student records and an index of all StudentRecords which
	 * are indexed by their jmbag for faster acquisition.
	 */
	private void createRecordsAndIndexes() {
		String[] elements = null;

		for (String row : lines) {
			elements = row.split("\t");

			validateDatabaseElements(elements);

			studentRecords.add(new StudentRecord(elements[0], elements[1], elements[2], Integer.parseInt(elements[3])));
			index.put(elements[0],
					new StudentRecord(elements[0], elements[1], elements[2], Integer.parseInt(elements[3])));
		}
	}

	/**
	 * Validates database elements from input text file.
	 *
	 * @param elements
	 *            the elements of from input text file
	 * @throws StudentDatabaseException
	 *             if input text file is not in valid format
	 */
	private void validateDatabaseElements(String[] elements) {
		if (elements.length != 4) {
			illegalStudentDatabaseState(
					"Invalid database.txt file, every row must consist of 4 attributes, was: " + elements.length);
		}

		if (!(elements[0] instanceof String)) {
			illegalStudentDatabaseState(
					"Invalid database.txt file, jmbag must be an instance of String, was: " + elements[0].getClass());
		}

		if (!(elements[1] instanceof String)) {
			illegalStudentDatabaseState("Invalid database.txt file, last name must be an instance of String, was: "
					+ elements[1].getClass());
		}

		if (!(elements[2] instanceof String)) {
			illegalStudentDatabaseState("Invalid database.txt file, first name must be an instance of String, was: "
					+ elements[2].getClass());
		}

		try {
			Integer.parseInt(elements[3]);
		} catch (NumberFormatException exc) {
			illegalStudentDatabaseState("Invalid database.txt file, final grade must be an instance of int.");
		}
	}

	/**
	 * Signigifies that the input text file is not in valid format and throws
	 * appropriate exception.
	 *
	 * @param message
	 *            the detail message of the invalid format
	 * @throws StudentDatabaseException
	 *             if input text file is not in valid format
	 */
	public static void illegalStudentDatabaseState(String message) {
		throw new StudentDatabaseException(message);
	}

	/**
	 * Gets the StudentRecord from the jmbag. This method uses an index of all
	 * StudentRecords which are indexed by their jmbag for faster acquisition.
	 *
	 * @param jmbag
	 *            the jmbag of the student
	 * @return the student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	/**
	 * Filters through StudentRecords and returns only those who meet certain
	 * criteria.
	 *
	 * @param filter
	 *            the instance of QueryFilter class
	 * @return the list of StudentRecords who meet certain criteria.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temporary = new LinkedList<>();

		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				temporary.add(record);
			}
		}

		return temporary;
	}

}
