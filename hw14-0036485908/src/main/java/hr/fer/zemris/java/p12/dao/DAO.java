package hr.fer.zemris.java.p12.dao;

import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface defining the methods for communicating with the database and
 * performing querys.
 * 
 * @author Damjan Vučina
 */
public interface DAO {

	/**
	 * Acquires all available polls from the database.
	 *
	 * @return the list
	 */
	List<Poll> acquirePolls();

	/**
	 * Acquires pollOptions for the defined poll.
	 *
	 * @param pollID
	 *            the poll ID
	 * @return the list of options for the designated poll
	 */
	List<PollOption> acquirePollOptions(long pollID);

	/**
	 * Performs voting for the designated option by increasing its vote count by
	 * one.
	 *
	 * @param optionID
	 *            the option ID
	 */
	void performVoting(long optionID);

	/**
	 * Identifies the poll based on its option.
	 *
	 * @param optionID
	 *            the chosen option
	 * @return the long id of the poll
	 */
	long identifyPoll(long optionID);

	/**
	 * Acquires PollOption-NumberOfVotes mappings for the specified poll.
	 *
	 * @param pollID
	 *            the poll whose results are to be extracted
	 * @return the map
	 */
	Map<String, Long> acquirePollResults(long pollID);

	/**
	 * Acquires PollOption-Link mappings for the specified poll and number of votes.
	 *
	 * @param pollID
	 *            the poll ID
	 * @param votesCount
	 *            the votes count
	 * @return the map
	 */
	Map<String, String> acquireReferences(Long pollID, Long votesCount);

}