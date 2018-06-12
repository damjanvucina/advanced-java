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
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

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

		// createDatabaseRelations(currentConnetion);
		// validateDatabaseRelations(currentConnetion);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	private void validateDatabaseRelations(Connection currentConnetion) {
		String isPollEmpty = "SELECT COUNT(*) FROM Polls";
		String isPollOptionsEmpty = "SELECT COUNT(*) FROM PollOptions";

		PreparedStatement pst = null;
		ResultSet pollResult = null;
		ResultSet pollOptionsResult = null;

		try {
			pst = currentConnetion.prepareStatement(isPollEmpty);
			pollResult = pst.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

//		try {
			// if (!pollResult.next()) {
			populatePollRelations(currentConnetion);
			// }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		try {
			pst = currentConnetion.prepareStatement(isPollOptionsEmpty);
			pollOptionsResult = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pollOptionsResult.getInt(1) == 0) {
				throw new IllegalStateException("PollOptions table should not be empty by now.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populatePollOptionsRelation(Connection currentConnetion, long generatedId) {
		PreparedStatement pst = null;

		try {
			pst = currentConnetion.prepareStatement("INSERT INTO PollOptions "
					+ "(optionTitle, optionLink, pollID, votesCount) VALUES\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0),\r\n"
					+ "('The Beatles','https://www.youtube.com/watch?v=z9ypq6_5bsg'," + generatedId + ", 0)");

			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populatePollRelations(Connection currentConnetion) {
		PreparedStatement pst = null;
		try {
			pst = currentConnetion.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, "Glasanje za omiljeni bend:");
			pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");

			pst.executeUpdate();

			int numberOfAffectedRows = pst.executeUpdate();
			if (numberOfAffectedRows != 1) {
				throw new IllegalStateException("Invalid number of rows affected, was: " + numberOfAffectedRows);
			}

			ResultSet rset = pst.getGeneratedKeys();

			try {
				if (rset != null && rset.next()) {
					long generatedId = rset.getLong(1);
					populatePollOptionsRelation(currentConnetion, generatedId);
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

			pollsResult = dbm.getTables(null, null, "Polls", null);
			if (!pollsResult.next()) {
				createPollsRelation(currentConnetion);
			}

			pollOptionsResult = dbm.getTables(null, null, "PollOptions", null);
			if (!pollOptionsResult.next()) {
				createPollOptionsRelation(currentConnetion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createPollOptionsRelation(Connection currentConnetion) {
		//@formatter:off
		String pollOptionsCreation = "CREATE TABLE PollOptions\r\n" + 
									 "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
									 "optionTitle VARCHAR(100) NOT NULL,\r\n" + 
									 "optionLink VARCHAR(150) NOT NULL,\r\n" + 
									 "pollID BIGINT,\r\n" + 
									 "votesCount BIGINT,\r\n" + 
									 "FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
									 ");";
		//@formatter:on
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
		String pollCreation = "CREATE TABLE Polls\r\n" + 
							  "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" +
							  "title VARCHAR(150) NOT NULL,\r\n" + 
							  "message CLOB(2048) NOT NULL\r\n" + 
							  ");";
		//@formatter:on

		PreparedStatement pst = null;

		try {
			pst = currentConnetion.prepareStatement(pollCreation);
			pst.execute();
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