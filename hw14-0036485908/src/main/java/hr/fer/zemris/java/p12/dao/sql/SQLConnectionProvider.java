package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * The class responsible for storing and acquiring the connections to the
 * database.
 * 
 * @author Damjan Vučina
 */
public class SQLConnectionProvider {

	/** The connections to the database. */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connection for the current thread, or removes the current connection if the argument is null.
	 *
	 * @param con
	 *            the current connection
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Gets the connection to be used by the current thread.
	 *
	 * @return the connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}