package hr.fer.zemris.java.p12.model;

public class PollOption {

	private long id;
	private String optionTitle;
	private String optionLink;
	private long pollID;
	private long votesCount;
	
	public PollOption() {
	}

	public long getId() {
		return id;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public long getPollID() {
		return pollID;
	}

	public long getVotesCount() {
		return votesCount;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

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
