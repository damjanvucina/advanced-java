package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Helper singleton class used for obtaining the reference to the
 * EntityManagerFactory object. Furthermore, this object is used for acquiring
 * entity manager objects that are used in the process of communicating with the
 * database.
 * 
 * @author Damjan Vučina
 */
public class JPAEMProvider {

	/** The thread local entity manager objects. */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Gets the entity manager objects that are used in the process of communicating
	 * with the database.
	 *
	 * @return the EntityManagerFactory object
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Method used for commiting the database query and closing entity manager
	 * objects.
	 *
	 * @throws DAOException
	 *             the DAO exception
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}