package hr.fer.zemris.java.hw05.db;

/**
 * The class that represents a single student and his data: first name, last
 * name and jmbag identification number.
 * 
 * @author Damjan Vučina
 */
public class StudentRecord {

	/** The jmbag of the student. */
	private String jmbag;

	/** The first name of the student. */
	private String firstName;

	/** The last name of the student. */
	private String lastName;

	/** The final grade of the student. */
	private int finalGrade;

	/**
	 * Instantiates a new student record with specified values.
	 *
	 * @param jmbag
	 *            the jmbag of the student
	 * @param lastName
	 *            the last name of the student
	 * @param firstName
	 *            the first name of the student
	 * @param finalGrade
	 *            the final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets the jmbag of the student.
	 *
	 * @return the jmbag of the student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the first name of the student.
	 *
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of the student.
	 *
	 * @return the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the final grade.
	 *
	 * @return the final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Sets the jmbag.
	 *
	 * @param jmbag
	 *            the new jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of the student.
	 *
	 * @param lastName
	 *            the new last name of the student
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the final grade of the student.
	 *
	 * @param finalGrade
	 *            the new final grade of the student
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets the attribute of the student.
	 *
	 * @param fieldGetter
	 *            the field getter
	 * @return the attribute of the student
	 */
	public String getAttribute(IFieldValueGetter fieldGetter) {
		if (fieldGetter.equals(FieldValueGetters.FIRST_NAME)) {
			return firstName;

		} else if (fieldGetter.equals(FieldValueGetters.LAST_NAME)) {
			return lastName;

		} else if (fieldGetter.equals(FieldValueGetters.JMBAG)) {
			return jmbag;
		}

		return null;
	}

	/**
	 * Checks if two instances of StudentRecord class are equal by calculating their
	 * hash. Two instances of StudentRecord class are considered equal if they have
	 * identical jmbag attribute.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Checks if two instances of StudentRecord class are equal. Two instances of
	 * StudentRecord class are considered equal if they have identical jmbag
	 * attribute.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Generates a string representation of this student record.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag).append(" ").append(lastName).append(" ").append(firstName).append(" ").append(finalGrade);
		return sb.toString();
	}
}
