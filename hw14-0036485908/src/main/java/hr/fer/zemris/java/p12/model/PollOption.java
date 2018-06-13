package hr.fer.zemris.java.p12.model;

/**
 * The class that defines a single poll option, defined by its id, title, link, designated poll and number of votes.
 */
public class PollOption {

	/** The id of the poll option. */
	private long id;
	
	/** The option title of the poll option. */
	private String optionTitle;
	
	/** The option link of the poll option. */
	private String optionLink;
	
	/** The poll ID of the poll option. */
	private long pollID;
	
	/** The votes count of the poll option. */
	private long votesCount;
	
	/**
	 * Instantiates a new poll option.
	 */
	public PollOption() {
	}
	
	/**
	 * Instantiates a new poll option.
	 *
	 * @param id the id of the poll option
	 * @param optionTitle the option title of the poll option
	 * @param optionLink the option link of the poll option
	 * @param pollID the poll ID of the poll option
	 * @param votesCount the votes count of the poll option
	 */
	public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Gets the id of the poll option.
	 *
	 * @return the id of the poll option
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the option title of the poll option.
	 *
	 * @return the option title of the poll option
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Gets the option link.
	 *
	 * @return the option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Gets the poll ID.
	 *
	 * @return the poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Gets the votes count.
	 *
	 * @return the votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the id of the poll option.
	 *
	 * @param id the new id of the poll option
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the option title of the poll option.
	 *
	 * @param optionTitle the new option title of the poll option
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Sets the option link of the poll option.
	 *
	 * @param optionLink the new option link of the poll option
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Sets the poll ID of the poll option.
	 *
	 * @param pollID the new poll ID of the poll option
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Sets the votes count of the poll option.
	 *
	 * @param votesCount the new votes count of the poll option
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * Checks if two instances of PollOption class are equal by calculating their
	 * hash. Two instances of PollOption class are considered equal if they have
	 * identical id attributes.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * Checks if two instances of PollOption class are equal. Two instances of
	 * PollOption class are considered equal if they have identical id attributes.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollOption other = (PollOption) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
