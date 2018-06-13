package hr.fer.zemris.java.p12.dao;

import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	
	List<Poll> acquirePolls();
	List<PollOption> acquirePollOptions(long pollID);
	void performVoting(long optionID);
	long identifyPoll(long optionID);
	Map<String, Long> acquirePollResults(long pollID);
	Map<String, String> acquireReferences(Long pollID, Long votesCount);
	
}