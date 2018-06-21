package hr.fer.zemris.java.tecaj_13.model;

/**
 * The class that represents a user's registration record whose attributes
 * correspond to the attributes of the FormularForm class.
 * 
 * @author Damjan Vučina
 */
public class Record implements Comparable<Record> {

	/** The id of the record. */
	private Long id;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The nick name. */
	private String nickName;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/**
	 * Instantiates a new record.
	 *
	 * @param id
	 *            the id of the record
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param nickName
	 *            the nick name
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 */
	public Record(Long id, String firstName, String lastName, String nickName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.email = email;
		this.password = password;
	}

	/**
	 * Instantiates a new record.
	 */
	public Record() {
	}

	/**
	 * Gets the id of the record.
	 *
	 * @return the id of the record
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the nick name.
	 *
	 * @return the nick name
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the id of the record.
	 *
	 * @param id
	 *            the new id of the record
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the nick name.
	 *
	 * @param nickName
	 *            the new nick name
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Checks whether two records are identical
	 */
	@Override
	public int compareTo(Record o) {
		if (this.id == null) {
			if (o.id == null)
				return 0;
			return -1;
		} else if (o.id == null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}

	/**
	 * Generates a string representation of the record.
	 */
	@Override
	public String toString() {
		return "{" + (id == null ? "?" : id.toString()) + " " + lastName + ", " + firstName + " (" + email + ")}";
	}
}
