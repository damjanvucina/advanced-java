package hr.fer.zemris.java.hw06.demo4;

/**
 * The class that is used for storing the information about a student and
 * accessing that information. Student's attributes are jmbag (10-digit
 * identification number), last name, first name, midterm points, final exam
 * points, laboratory exercises point and final grade.
 * 
 * @author Damjan Vučina
 */
public class StudentRecord {

	/** The student's jmbag. */
	private String jmbag;

	/** The student's last name. */
	private String lastName;

	/** The student's first name. */
	private String firstName;

	/** The student's midterm points. */
	private double midtermPoints;

	/** The student's final exam points. */
	private double finalExamPoints;

	/** The student's laboratory points. */
	private double laboratoryPoints;

	/** The student's grade. */
	private int grade;

	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag
	 *            the student's jmbag
	 * @param lastName
	 *            the student's last name
	 * @param firstName
	 *            the student's first name
	 * @param midtermPoints
	 *            the student's midterm points
	 * @param finalExamPoints
	 *            the student's final exam points
	 * @param laboratoryPoints
	 *            the student's laboratory points
	 * @param grade
	 *            the student's grade
	 */
	//@formatter:off
	public StudentRecord(String jmbag, String lastName, String firstName, 
						double midtermPoints, double finalExamPoints, 
						double laboratoryPoints, int grade) {
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermPoints = midtermPoints;
		this.finalExamPoints = finalExamPoints;
		this.laboratoryPoints = laboratoryPoints;
		this.grade = grade;
	}
	//@formatter:on

	/**
	 * Gets the student's jmbag.
	 *
	 * @return the student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the student's last name.
	 *
	 * @return the student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the student's first name.
	 *
	 * @return the student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the student's midterm points.
	 *
	 * @return the student's midterm points
	 */
	public double getMidtermPoints() {
		return midtermPoints;
	}

	/**
	 * Gets the student's final exam points.
	 *
	 * @return the student's final exam points
	 */
	public double getFinalExamPoints() {
		return finalExamPoints;
	}

	/**
	 * Gets the student's laboratory points.
	 *
	 * @return the student's laboratory points
	 */
	public double getLaboratoryPoints() {
		return laboratoryPoints;
	}

	/**
	 * Gets the student's grade.
	 *
	 * @return the student's grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Gets the student's total points.
	 *
	 * @return the student's total points
	 */
	public double getTotalPoints() {
		return midtermPoints + finalExamPoints + laboratoryPoints;
	}

	/**
	 * Sets the student's jmbag.
	 *
	 * @param jmbag
	 *            the new student's jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Sets the student's last name.
	 *
	 * @param lastName
	 *            the new student's last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the student's first name.
	 *
	 * @param firstName
	 *            the new student's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the student's midterm points.
	 *
	 * @param midtermPoints
	 *            the new student's midterm points
	 */
	public void setMidtermPoints(double midtermPoints) {
		this.midtermPoints = midtermPoints;
	}

	/**
	 * Sets the final exam points.
	 *
	 * @param finalExamPoints
	 *            the new student's final exam points
	 */
	public void setFinalExamPoints(double finalExamPoints) {
		this.finalExamPoints = finalExamPoints;
	}

	/**
	 * Sets the laboratory points.
	 *
	 * @param laboratoryPoints
	 *            the new student's laboratory points
	 */
	public void setLaboratoryPoints(double laboratoryPoints) {
		this.laboratoryPoints = laboratoryPoints;
	}

	/**
	 * Sets the student's grade.
	 *
	 * @param grade
	 *            the student's new grade
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Returns the String representation of the student
	 */
	//@formatter:off
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag).append(" ")
		  .append(firstName).append(" ")
		  .append(lastName).append(" ")
		  .append(midtermPoints).append(" ")
		  .append(finalExamPoints).append(" ")
		  .append(laboratoryPoints).append(" ")
		  .append(grade);
		
		return sb.toString();
	}
	//@formatter:on
}
