package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * The singleton class used for obtaining the entity manager factory object
 * responsible for context initialization.
 * 
 * @author Damjan Vučina
 */
public class JPAEMFProvider {

	/**
	 * The reference to the entity manager factory object responsible for context
	 * initialization..
	 */
	public static EntityManagerFactory emf;

	/**
	 * Gets the entity manager factory object responsible for context
	 * initialization.
	 *
	 * @return the entity manager factory object responsible for context
	 *         initialization.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the entity manager factory object responsible for context
	 * initialization.
	 *
	 * @param emf
	 *            the new entity manager factory object responsible for context
	 *            initialization.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}