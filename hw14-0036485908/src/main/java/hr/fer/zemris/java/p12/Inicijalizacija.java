package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties properties = new Properties();
		String path = "WEB-INF/dbsettings.properties";

		try {
			properties.load(new FileInputStream(sce.getServletContext().getRealPath(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String propHost = properties.getProperty("host");
		String propPort = properties.getProperty("port");
		String propDBName = properties.getProperty("name");
		String propUser = properties.getProperty("user");
		String propPassword = properties.getProperty("password");

		String connectionURL = "jdbc:derby://" + propHost + ":" + propPort + "/" + propDBName + ";user=" + propUser
				+ ";password=" + propPassword;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error initializing pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		Connection currentConnetion = null;
		try {
			currentConnetion = cpds.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		createDatabaseRelations(currentConnetion);
		validateDatabaseRelations(currentConnetion);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	private void validateDatabaseRelations(Connection currentConnetion) {
		String isPollEmpty = "SELECT * FROM POLLS";
		String isPollOptionsEmpty = "SELECT * FROM POLLOPTIONS";

		PreparedStatement pst = null;
		ResultSet pollResult = null;
		ResultSet pollOptionsResult = null;

		try {
			pst = currentConnetion.prepareStatement(isPollEmpty);
			pollResult = pst.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pollResult == null || !pollResult.next()) {
				fillDummyDatabase(currentConnetion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			pst = currentConnetion.prepareStatement(isPollOptionsEmpty);
			pollOptionsResult = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pollOptionsResult == null || !pollOptionsResult.next()) {
				throw new IllegalStateException("PollOptions table should not be empty by now.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillDummyDatabase(Connection currentConnetion) {
		String firstTitle = "Glasanje za omiljeni bend:";
		String firstMessage = "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!";

		List<PollOption> options = new ArrayList<>();
		options.add(new PollOption(-1, "The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", -1, 0));
		options.add(new PollOption(-1, "The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", -1, 0));
		options.add(new PollOption(-1, "The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", -1, 0));
		options.add(new PollOption(-1, "The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", -1, 0));
		options.add(new PollOption(-1, "The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", -1, 0));
		options.add(new PollOption(-1, "The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", -1, 0));
		options.add(
				new PollOption(-1, "The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", -1, 0));

		populatePollRelations(currentConnetion, new Poll(1, firstTitle, firstMessage), options);

		String secondTitle = "Lakers' best players";
		String secondMessage = "Who is the all-time best Laker";

		options.clear();
		options.add(new PollOption(-1, "Shaq Diesel", "https://www.basketball-reference.com/players/o/onealsh01.html",
				-1, 0));
		options.add(new PollOption(-1, "Black Mamba", "https://www.basketball-reference.com/players/b/bryanko01.html",
				-1, 0));
		options.add(
				new PollOption(-1, "Magic", "https://www.basketball-reference.com/players/j/johnsma02.html", -1, 0));
		options.add(
				new PollOption(-1, "Kareem", "https://www.basketball-reference.com/players/a/abdulka01.html", -1, 0));
		options.add(new PollOption(-1, "Wilt", "https://www.basketball-reference.com/players/c/chambwi01.html", -1, 0));
		options.add(
				new PollOption(-1, "The Logo", "https://www.basketball-reference.com/players/w/westje01.html", -1, 0));
		options.add(new PollOption(-1, "Big Game James",
				"https://www.basketball-reference.com/players/w/worthja01.html", -1, 0));
		options.add(new PollOption(-1, "Mr.Inside", "https://www.basketball-reference.com/players/b/bayloel01.html", -1,
				0));

		populatePollRelations(currentConnetion, new Poll(1, secondTitle, secondMessage), options);
	}

	private void populatePollOptionsRelation(Connection currentConnetion, long generatedId, List<PollOption> options) {
		PreparedStatement pst = null;

		try {

			for (PollOption option : options) {
				pst = currentConnetion.prepareStatement(
						"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");

				pst.setString(1, option.getOptionTitle());
				pst.setString(2, option.getOptionLink());
				pst.setLong(3, generatedId);
				pst.setLong(4, 0);

				pst.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populatePollRelations(Connection currentConnetion, Poll poll, List<PollOption> options) {
		PreparedStatement pst = null;
		try {
			pst = currentConnetion.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());

			int numberOfAffectedRows = pst.executeUpdate();

			if (numberOfAffectedRows != 1) {
				throw new IllegalStateException("Invalid number of rows affected, was: " + numberOfAffectedRows);
			}

			ResultSet rset = pst.getGeneratedKeys();

			try {
				if (rset != null && rset.next()) {
					long generatedId = rset.getLong(1);
					populatePollOptionsRelation(currentConnetion, generatedId, options);
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void createDatabaseRelations(Connection currentConnetion) {
		ResultSet pollsResult = null;
		ResultSet pollOptionsResult = null;

		try {
			DatabaseMetaData dbm = currentConnetion.getMetaData();

			pollsResult = dbm.getTables(null, null, "POLLS", null);
			if (!pollsResult.next()) {
				createPollsRelation(currentConnetion);
			}

			pollOptionsResult = dbm.getTables(null, null, "POLLOPTIONS", null);
			if (!pollOptionsResult.next()) {
				createPollOptionsRelation(currentConnetion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createPollOptionsRelation(Connection currentConnetion) {
		String pollOptionsCreation = "CREATE TABLE PollOptions(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,optionTitle VARCHAR(100) NOT NULL,optionLink VARCHAR(150) NOT NULL,pollID BIGINT,votesCount BIGINT,FOREIGN KEY (pollID) REFERENCES Polls(id))";
		PreparedStatement pst = null;
		try {
			pst = currentConnetion.prepareStatement(pollOptionsCreation);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				pst.close();
			} catch (SQLException | NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void createPollsRelation(Connection currentConnetion) {
		//@formatter:off
		String pollCreation = "CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)";
		//@formatter:on

		PreparedStatement pst = null;

		try {
			pst = currentConnetion.prepareStatement(pollCreation);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}