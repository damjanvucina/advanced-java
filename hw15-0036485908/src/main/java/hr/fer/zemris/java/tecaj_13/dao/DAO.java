package hr.fer.zemris.java.tecaj_13.dao;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;
	
	BlogUser validateLogin(HttpServletRequest req, String nickName, String password);
	
	BlogUser createNewUser(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp, FormularForm f);

}