package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The class responsible for communicating with the database and performing
 * queries defined by the DAO interface.
 * 
 * @author Damjan Vučina
 */
public class SQLDAO implements DAO {

	/**
	 * Acquires all available polls from the database.
	 *
	 * @return the list
	 */
	@Override
	public List<Poll> acquirePolls() {
		List<Poll> pollList = new ArrayList<>();

		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();

						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(2));

						pollList.add(poll);
					}

				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}

			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return pollList;
	}

	/**
	 * Acquires PollOption-NumberOfVotes mappings for the specified poll.
	 *
	 * @param pollID
	 *            the poll whose results are to be extracted
	 * @return the map
	 */
	@Override
	public Map<String, Long> acquirePollResults(long pollID) {
		Map<String, Long> options = new LinkedHashMap<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		String query = "select optionTitle, votesCount from POLLOPTIONS where pollID=" + pollID
				+ " order by votesCount DESC";

		try {
			pst = con.prepareStatement(query);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						options.put(rs.getString(1), rs.getLong(2));
					}

				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}

			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}

		return options;
	}

	/**
	 * Performs voting for the designated option by increasing its vote count by
	 * one.
	 *
	 * @param optionID
	 *            the option ID
	 */
	@Override
	public void performVoting(long optionID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = " + optionID);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Identifies the poll based on its option.
	 *
	 * @param optionID
	 *            the chosen option
	 * @return the long id of the poll
	 */
	@Override
	public long identifyPoll(long optionID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Long value = null;

		try {
			pst = con.prepareStatement("SELECT POLLID FROM POLLOPTIONS WHERE ID = " + optionID,
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				value = rs.getLong(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * Acquires pollOptions for the defined poll.
	 *
	 * @param pollID
	 *            the poll ID
	 * @return the list of options for the designated poll
	 */
	@Override
	public List<PollOption> acquirePollOptions(long pollID) {
		List<PollOption> optionList = new ArrayList<>();

		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, pollID FROM POLLOPTIONS WHERE pollID = " + pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();

						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollID(rs.getLong(4));

						optionList.add(option);
					}

				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}

			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return optionList;
	}

	/**
	 * Acquires PollOption-Link mappings for the specified poll and number of votes.
	 *
	 * @param pollID
	 *            the poll ID
	 * @param votesCount
	 *            the votes count
	 * @return the map
	 */
	@Override
	public Map<String, String> acquireReferences(Long pollID, Long votesCount) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Map<String, String> references = new LinkedHashMap<>();

		String query = "SELECT optionTitle, optionLink FROM POLLOPTIONS WHERE pollID=" + pollID + "and votesCount="
				+ votesCount;

		try {
			pst = con.prepareStatement(query);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						references.put(rs.getString(1), rs.getString(2));
					}

				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}

			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return references;
	}
}