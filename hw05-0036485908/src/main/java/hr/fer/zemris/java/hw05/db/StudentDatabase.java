package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class StudentDatabase{

	private List<String> lines;
	private List<StudentRecord> studentRecords;
	private SimpleHashtable<String, StudentRecord> index;

	public StudentDatabase(List<String> lines) {
		this.lines = lines;
		
		studentRecords = new LinkedList<>();
		index = new SimpleHashtable<>(lines.size());
		
		createRecordsAndIndexes();
	}

	public List<StudentRecord> getStudentRecords() {
		return studentRecords;
	}

	private void createRecordsAndIndexes() {
		String[] elements = null;

		for (String row : lines) {
			elements = row.split("\t");

			validateDatabaseElements(elements);

			studentRecords.add(new StudentRecord(elements[0], elements[1], elements[2], Integer.parseInt(elements[3])));
			index.put(elements[0], new StudentRecord(elements[0], elements[1], elements[2], Integer.parseInt(elements[3])));
		}
	}

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

	private void illegalStudentDatabaseState(String message) {
		throw new StudentDatabaseException(message);
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> temporary = new LinkedList<>();
		
		for(StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				temporary.add(record);
			}
		}
		
		return temporary;
	}


}
