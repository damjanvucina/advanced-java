package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.model.Record;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * The class that represents a regitration form for this blog.
 * 
 * @author Damjan Vučina
 */
public class FormularForm {
	
	/** The Constant FIRST_NAME_ERROR. */
	public static final String FIRST_NAME_ERROR = "firstNameError";
	
	/** The Constant LAST_NAME_ERROR. */
	public static final String LAST_NAME_ERROR = "lastNameError";
	
	/** The Constant NICK_NAME_ERROR. */
	public static final String NICK_NAME_ERROR = "nickNameError";
	
	/** The Constant EMAIL_ERROR. */
	public static final String EMAIL_ERROR = "emailError";
	
	/** The Constant PASSWORD_ERROR. */
	public static final String PASSWORD_ERROR = "passwordError";

	/** The id of the user. */
	private String id;
	
	/** The first name of the user. */
	private String firstName;
	
	/** The last name of the user. */
	private String lastName;
	
	/** The nick name of the user. */
	private String nickName;
	
	/** The email of the user. */
	private String email;
	
	/** The password of the user. */
	private String password;

	/** The errors. */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Instantiates a new formular form.
	 */
	public FormularForm() {
	}

	/**
	 * Acquires specified error.
	 *
	 * @param attribute the attribute
	 * @return the string
	 */
	public String acquireError(String attribute) {
		return errors.get(attribute);
	}

	/**
	 * Checks if any errors exist.
	 *
	 * @return true, if successful
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Checks for specific error.
	 *
	 * @param attribute the attribute
	 * @return true, if successful
	 */
	public boolean hasError(String attribute) {
		return errors.containsKey(attribute);
	}

	/**
	 * Initializes FormularForm from HTTPServletRequest object
	 *
	 * @param req the req
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		this.id = format(req.getParameter("id"));
		this.firstName = format(req.getParameter("firstName"));
		this.lastName = format(req.getParameter("lastName"));
		this.nickName = format(req.getParameter("nickName"));
		this.email = format(req.getParameter("email"));
		this.password = format(req.getParameter("password"));
	}

	/**
	 * Initializes FormularForm from Record object
	 *
	 * @param r the r
	 */
	public void fromRecord(Record r) {
		if (r.getId() == null) {
			id = "";
		} else {
			this.id = r.getId().toString();
		}

		firstName = r.getFirstName();
		lastName = r.getLastName();
		nickName = r.getNickName();
		email = r.getEmail();
		password = r.getPassword();
	}

	/**
	 * Transforms FormularForm object to Record object
	 *
	 * @param r the r
	 */
	public void toRecord(Record r) {
		if (id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(id));
		}

		r.setFirstName(firstName);
		r.setLastName(lastName);
		r.setNickName(nickName);
		r.setPassword(password);
		r.setEmail(email);
	}

	/**
	 * Validates possible errors.
	 */
	public void validate() {
		errors.clear();

		if (!id.isEmpty()) {
			try {
				Long.parseLong(id);
			} catch (NumberFormatException ex) {
				errors.put("id", "Invalid id given, was:" + id);
			}
		}

		if (firstName.isEmpty()) {
			errors.put(FIRST_NAME_ERROR, "First name cannot be empty");
		}

		if (lastName.isEmpty()) {
			errors.put(LAST_NAME_ERROR, "Last name cannot be empty");
		}

		if (nickName.isEmpty()) {
			errors.put(NICK_NAME_ERROR, "Nick name cannot be empty");
		}

		if (email.isEmpty()) {
			errors.put(EMAIL_ERROR, "Email cannot be empty");
		} else {
			int l = email.length();
			int p = email.indexOf('@');

			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put(EMAIL_ERROR, "Invalid email format.");
			}
		}
		
		if (password.isEmpty()) {
			errors.put(PASSWORD_ERROR, "Password cannot be empty");
		}
	}

	/**
	 * Formats the input by trimming it.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String format(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
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
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the nick name.
	 *
	 * @param nickName the new nick name
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the errors
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
