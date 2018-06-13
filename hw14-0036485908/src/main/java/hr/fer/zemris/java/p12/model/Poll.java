package hr.fer.zemris.java.p12.model;

/**
 * The class that represents a single poll, defined by its id, name and message.
 * 
 * @author Damjan Vučina
 */
public class Poll {

	/** The id of the poll. */
	private long id;
	
	/** The title of the poll. */
	private String title;
	
	/** The message of the poll. */
	private String message;
	
	/**
	 * Instantiates a new poll.
	 */
	public Poll() {
	}
	
	/**
	 * Instantiates a new poll.
	 *
	 * @param id the id of the poll
	 * @param title the title of the poll
	 * @param message the message of the poll
	 */
	public Poll(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Gets the id of the poll.
	 *
	 * @return the id of the poll
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the title of the poll.
	 *
	 * @return the title of the poll
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the message of the poll.
	 *
	 * @return the message of the poll
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the id of the poll.
	 *
	 * @param id the new id of the poll
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the title of the poll.
	 *
	 * @param title the new title of the poll
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the message of the poll.
	 *
	 * @param message the new message of the poll
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Checks if two instances of Poll class are equal by calculating their
	 * hash. Two instances of Poll class are considered equal if they have
	 * identical id attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Checks if two instances of Poll class are equal. Two instances of
	 * Poll class are considered equal if they have identical id attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poll other = (Poll) obj;
		if (id != other.id)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
