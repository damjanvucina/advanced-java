package hr.fer.zemris.java.hw06.demo4;

public class StudentRecord{
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private double midtermPoints;
	private double finalExamPoints;
	private double laboratoryPoints;
	private int grade;

	//@formatter:off
	public StudentRecord(String jmbag, String lastName, String firstName, 
			double midtermPoints, double finalExamPoints, double laboratoryPoints, int grade) {
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermPoints = midtermPoints;
		this.finalExamPoints = finalExamPoints;
		this.laboratoryPoints = laboratoryPoints;
		this.grade = grade;
	}
	//@formatter:on

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public double getMidtermPoints() {
		return midtermPoints;
	}

	public double getFinalExamPoints() {
		return finalExamPoints;
	}

	public double getLaboratoryPoints() {
		return laboratoryPoints;
	}

	public int getGrade() {
		return grade;
	}

	public double getTotalPoints() {
		return midtermPoints + finalExamPoints + laboratoryPoints;
	}

	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMidtermPoints(double midtermPoints) {
		this.midtermPoints = midtermPoints;
	}

	public void setFinalExamPoints(double finalExamPoints) {
		this.finalExamPoints = finalExamPoints;
	}

	public void setLaboratoryPoints(double laboratoryPoints) {
		this.laboratoryPoints = laboratoryPoints;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

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
