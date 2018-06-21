package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class that represents a blog post that is defines by its comments,
 * creation and last modification timestamps, title, text as well as a reference
 * to the author.
 * 
 * @author Damjan Vučina
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** The id of the entry. */
	private Long id;

	/** The comments. */
	private List<BlogComment> comments = new ArrayList<>();

	/** The created at timestamp. */
	private Date createdAt;

	/** The last modified at timestamp. */
	private Date lastModifiedAt;

	/** The title of the blog entry. */
	private String title;

	/** The text of the blog entry. */
	private String text;

	/** The creator . */
	private BlogUser creator;

	/**
	 * Gets the creator of the blog entry.
	 *
	 * @return the creator of the blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets the creator of the blog entry.
	 *
	 * @param creator
	 *            the new creator of the blog entry
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Gets the id of the blog entry.
	 *
	 * @return the id of the blog entry
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of the blog entry.
	 *
	 * @param id
	 *            the new id of the blog entry
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the comments of this blog entry.
	 *
	 * @return the comments of this blog entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments of this blog entry.
	 *
	 * @param comments
	 *            the new comments of this blog entry
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Gets the created at timestamp.
	 *
	 * @return the created at timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at at timestamp.
	 *
	 * @param createdAt
	 *            the new created at at timestamp
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the last modified at at timestamp.
	 *
	 * @return the last modified at at timestamp
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the last modified at at timestamp.
	 *
	 * @param lastModifiedAt
	 *            the new last modified at at timestamp
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Checks if two instances of BlogEntry class are equal by calculating their
	 * hash. Two instances of BlogEntry class are considered equal if they have
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
	 * Checks if two instances of BlogEntry class are equal. Two instances of
	 * BlogEntry class are considered equal if they have identical id attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}