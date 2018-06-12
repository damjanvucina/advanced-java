package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mchange.io.impl.EndsWithFilenameFilter;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

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
	
	@Override
	public Map<String, Integer> acquirePollResults(long pollID) {
		Map<String, Integer> temp = new HashMap<>();
		
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
		
	}

	@Override
	public void performVoting(long optionID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = " + optionID + "SET ");
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long identifyPoll(long optionID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"SELECT pollID FROM PollOptions WHERE optionID = " + optionID);
			ResultSet rs = pst.executeQuery();
			return rs.getLong(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public List<PollOption> acquirePollOptions(long pollID) {
		List<PollOption> optionList = new ArrayList<>();

		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, pollID FROM PollOptions WHERE pollID = " + pollID);
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
}