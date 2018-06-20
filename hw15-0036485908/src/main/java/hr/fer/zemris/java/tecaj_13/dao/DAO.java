package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

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
	
	List<BlogEntry> acquireUserEntries(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp, Long authorID);
	
	BlogUser validateLogin(HttpServletRequest req, String nickName, String password);
	
	BlogUser createNewUser(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp, FormularForm f);
	
	List<BlogUser> acquireRegisteredAuthors(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp);
	
	Long acquireUserID(EntityManagerFactory emf, String nickName);
	
	BlogUser acquireUser(EntityManagerFactory emf, String nickName);
	
	void performDatabaseInput(EntityManagerFactory emf, Object obj);

	List<BlogEntry> acquireUserEntries2(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp,
			BlogUser user);
}