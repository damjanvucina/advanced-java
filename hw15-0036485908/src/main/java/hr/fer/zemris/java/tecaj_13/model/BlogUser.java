package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The class that represent the user of the blog. A user is defined by its first
 * name and last name, nickname, email, a SHA1 representation of the password
 * and a list of the user's blog entries.
 * 
 * @author Damjan Vučina
 */
@Entity
@Table(name = "blog_user")
public class BlogUser {

	/** The id of the user. */
	private Long id;

	/** The first name of the user. */
	private String firstName;

	/** The last name of the user. */
	private String lastName;

	/** The nick name of the user. */
	private String nickName;

	/** The email of the user. */
	private String email;

	/** The password hash. */
	private String passwordHash;

	/** The entries of the user. */
	private Collection<BlogEntry> entries = new ArrayList<>();

	/**
	 * Gets the id of the user.
	 *
	 * @return the id of the user
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name of the user
	 */
	@Column(length = 100, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the nick name.
	 *
	 * @return the nick name
	 */
	@Column(length = 100, nullable = false, unique = true)
	public String getNickName() {
		return nickName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the password hash of the user.
	 *
	 * @return the password hash of the user
	 */
	@Column(length = 1000, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the id of the user.
	 *
	 * @param id
	 *            the new id of the user
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
	 * Sets the last name of the user.
	 *
	 * @param lastName
	 *            the new last name of the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the nick name of the user.
	 *
	 * @param nickName
	 *            the new nick name of the user
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Sets the email of the user.
	 *
	 * @param email
	 *            the new email of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash
	 *            the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	@OneToMany(mappedBy = "creator")
	public Collection<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the entries.
	 *
	 * @param entries
	 *            the new entries
	 */
	public void setEntries(Collection<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Checks if two instances of BlogUser class are equal by calculating their
	 * hash. Two instances of BlogUser class are considered equal if they have
	 * identical id attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Checks if two instances of BlogUser class are equal. Two instances of
	 * BlogUser class are considered equal if they have identical id attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
