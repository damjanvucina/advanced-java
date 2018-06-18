package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.model.Record;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FormularForm {
	public static final String FIRST_NAME_ERROR = "firstNameError";
	public static final String LAST_NAME_ERROR = "lastNameError";
	public static final String NICK_NAME_ERROR = "nickNameError";
	public static final String EMAIL_ERROR = "emailError";
	public static final String PASSWORD_ERROR = "passwordError";

	private String id;
	private String firstName;
	private String lastName;
	private String nickName;
	private String email;
	private String password;

	Map<String, String> errors = new HashMap<>();

	public FormularForm() {
	}

	public String acquireError(String attribute) {
		return errors.get(attribute);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String attribute) {
		return errors.containsKey(attribute);
	}

	public void fromHttpRequest(HttpServletRequest req) {
		this.id = format(req.getParameter("id"));
		this.firstName = format(req.getParameter("firstName"));
		this.lastName = format(req.getParameter("lastName"));
		this.nickName = format(req.getParameter("nickName"));
		this.email = format(req.getParameter("email"));
		this.password = format(req.getParameter("password"));
	}

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

	private String format(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
