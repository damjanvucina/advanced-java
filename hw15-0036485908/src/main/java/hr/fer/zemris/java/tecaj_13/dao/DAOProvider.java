package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Helper class used for obtaining the object responsible for communicating with
 * the database and performing queries.
 */
public class DAOProvider {

	/**
	 * The reference to the object assigned with the task of providing communication
	 * with the database
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Gets the object responsible for communicating with the database
	 *
	 * @return the dao
	 */
	public static DAO getDAO() {
		return dao;
	}

}