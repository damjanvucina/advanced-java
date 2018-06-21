package hr.fer.zemris.java.tecaj_13.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

/**
 * Interface defining the methods used for the purpose of communicating with the
 * database and performing queries.
 * 
 * @author Damjan Vučina
 */
public interface DAO {

	/**
	 * Gets the blog entry specified by its id attribute.
	 *
	 * @param id
	 *            the id of the blog entry
	 * @return the blog entry
	 * @throws DAOException
	 *             the DAO exception
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Acquire user entries.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param authorID
	 *            the author ID
	 * @return the list
	 */
	List<BlogEntry> acquireUserEntries(HttpServletRequest req, HttpServletResponse resp, Long authorID);

	/**
	 * Validates the login form by checking whether the provided nickname and
	 * password match the values stored in the database. NOTICE: password is not
	 * stored in plain text form, its value is digested using SHA1 algorithm and
	 * stored as such
	 *
	 * @param req
	 *            the request
	 * @param nickName
	 *            the nick name
	 * @param password
	 *            the password
	 * @return the blog user
	 */
	BlogUser validateLogin(HttpServletRequest req, String nickName, String password);

	/**
	 * Creates a new blog user based on the parameters provided within the formular form.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @param f
	 *            the f
	 * @return the blog user
	 */
	BlogUser createNewUser(HttpServletRequest req, HttpServletResponse resp, FormularForm f);

	/**
	 * Acquires registered authors from the database.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @return the list
	 */
	List<BlogUser> acquireRegisteredAuthors(HttpServletRequest req, HttpServletResponse resp);

	/**
	 * Acquire user's ID from its nickname.
	 *
	 * @param nickName
	 *            the nick name of the user
	 * @return the long
	 */
	Long acquireUserID(String nickName);

	/**
	 * Acquires reference to the used from its nickname.
	 *
	 * @param nickName
	 *            the nick name
	 * @return the blog user
	 */
	BlogUser acquireUser(String nickName);

	/**
	 * Performs database input by storing the provided argument to the database.
	 *
	 * @param obj
	 *            the obj
	 */
	void performDatabaseInput(Object obj);

	/**
	 * Acquires list of blog comments for the blog entry specified by its id.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param entryID
	 *            the entry ID
	 * @return the list
	 */
	List<BlogComment> acquireBlogComments(HttpServletRequest req, HttpServletResponse resp, Long entryID);

	/**
	 * Updates blog entry by updating its title, text and lastModifiedAt timestamp.
	 *
	 * @param entryID
	 *            the entry ID
	 * @param updatedTitle
	 *            the updated title
	 * @param updatedText
	 *            the updated text
	 * @param now
	 *            the now
	 */
	void updateBlogEntry(Long entryID, String updatedTitle, String updatedText, Date now);

}