package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class that represents a blog comment that is defined by its supervising
 * blog entry reference, commenter's mail address as well as message text and
 * posting timestamp.
 * 
 * @author Damjan Vučina
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/** The id of the comment. */
	private Long id;

	/** The blog entry this comment belongs to. */
	private BlogEntry blogEntry;

	/** The author's mail. */
	private String usersEMail;

	/** The message. */
	private String message;

	/** The time of posting. */
	private Date postedOn;

	/**
	 * Gets the id of the comment.
	 *
	 * @return the id of the comment.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of the comment..
	 *
	 * @param id
	 *            the new id of the comment.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the blog entry.
	 *
	 * @return the blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the blog entry.
	 *
	 * @param blogEntry
	 *            the new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets the users E mail.
	 *
	 * @return the users E mail
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the users E mail.
	 *
	 * @param usersEMail
	 *            the new users E mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the posted on timestamp.
	 *
	 * @return the posted on timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the posted on timestamp.
	 *
	 * @param postedOn
	 *            the new posted on timestamp
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * Checks if two instances of BlogComment class are equal by calculating their
	 * hash. Two instances of BlogComment class are considered equal if they have
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
	 * Checks if two instances of BlogComment class are equal. Two instances of
	 * BlogComment class are considered equal if they have identical id attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}