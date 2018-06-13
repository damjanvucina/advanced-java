package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * The class assigned with the task of acquiring the object responsible for
 * providing a communication with the database.
 * 
 * @author Damjan Vučina
 */
public class DAOProvider {

	/** The object providing a communication with the database. */
	private static DAO dao = new SQLDAO();

	/**
	 * Gets the dao, i.e. object providing a communication with the database.
	 *
	 * @return the providing a communication with the database
	 */
	public static DAO getDao() {
		return dao;
	}

}