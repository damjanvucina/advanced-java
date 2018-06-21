package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

/**
 * The class responsible for providing methods used for communicating with the
 * database.
 */
public class JPADAOImpl implements DAO {

	/**
	 * Gets the blog entry specified by its id attribute.
	 *
	 * @param id
	 *            the id of the blog entry
	 * @return the blog entry
	 * @throws DAOException
	 *             the DAO exception
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

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
	@Override
	public BlogUser validateLogin(HttpServletRequest req, String nickName, String password) {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = null;
		try {
			user = em
					.createQuery(
							"select b from BlogUser as b where b.nickName=:nickName and b.passwordHash=:passwordHash",
							BlogUser.class)
					.setParameter("nickName", nickName).setParameter("passwordHash", toSHA1(password))
					.getSingleResult();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();
		return user;
	}

	/**
	 * Helper method used for transforming user's password to an unrecognizable form
	 * prior to storing it to the database.
	 * 
	 * @param input
	 * @return
	 */
	public String toSHA1(String input) {
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * Creates a new blog user based on the parameters provided within the formular
	 * form.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @param f
	 *            the f
	 * @return the blog user
	 */
	@Override
	public BlogUser createNewUser(HttpServletRequest req, HttpServletResponse resp, FormularForm f) {

		BlogUser user = new BlogUser();
		user.setFirstName(f.getFirstName());
		user.setLastName(f.getLastName());
		user.setNickName(f.getNickName());
		user.setPasswordHash(f.getPassword());
		user.setEmail(f.getEmail());
		user.setPasswordHash(toSHA1(f.getPassword()));

		JPAEMProvider.getEntityManager().persist(user);
		JPAEMProvider.close();

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.tecaj_13.dao.DAO#acquireRegisteredAuthors(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public List<BlogUser> acquireRegisteredAuthors(HttpServletRequest req, HttpServletResponse resp) {

		EntityManager em = JPAEMProvider.getEntityManager();

		List<BlogUser> registeredAuthors = null;
		try {
			registeredAuthors = em.createQuery("select b from BlogUser as b", BlogUser.class).getResultList();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();
		return registeredAuthors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.tecaj_13.dao.DAO#acquireUserEntries(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Long)
	 */
	@Override
	public List<BlogEntry> acquireUserEntries(HttpServletRequest req, HttpServletResponse resp, Long authorID) {

		EntityManager em = JPAEMProvider.getEntityManager();

		List<BlogEntry> userEntries = null;

		try {
			userEntries = em
					.createQuery("select b from BlogEntry as b where b.creator.id=:creatorID order by b.createdAt desc",
							BlogEntry.class)
					.setParameter("creatorID", authorID).getResultList();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();
		return userEntries;
	}

	/**
	 * Acquire user's ID from its nickname.
	 *
	 * @param nickName
	 *            the nick name of the user
	 * @return the long
	 */
	@Override
	public Long acquireUserID(String nickName) {
		EntityManager em = JPAEMProvider.getEntityManager();

		Long userID = null;

		try {
			userID = em.createQuery("select b.id from BlogUser as b where b.nickName=:nickName", Long.class)
					.setParameter("nickName", nickName).getSingleResult();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();
		return userID;
	}

	/**
	 * Performs database input by storing the provided argument to the database.
	 *
	 * @param obj
	 *            the obj
	 */
	@Override
	public void performDatabaseInput(Object obj) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(obj);
		JPAEMProvider.close();
	}

	/**
	 * Acquires reference to the used from its nickname.
	 *
	 * @param nickName
	 *            the nick name
	 * @return the blog user
	 */
	@Override
	public BlogUser acquireUser(String nickName) {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = null;

		try {
			user = em.createQuery("select b from BlogUser as b where b.nickName=:nickName", BlogUser.class)
					.setParameter("nickName", nickName).getSingleResult();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();
		return user;
	}

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
	@Override
	public List<BlogComment> acquireBlogComments(HttpServletRequest req, HttpServletResponse resp, Long entryID) {
		EntityManager em = JPAEMProvider.getEntityManager();

		List<BlogComment> blogComments = null;

		try {
			blogComments = em.createQuery(
					"select b from BlogComment as b where b.blogEntry.id=:entryID order by b.postedOn desc",
					BlogComment.class).setParameter("entryID", entryID).getResultList();
		} catch (NoResultException ignorable) {
		}

		JPAEMProvider.close();

		return blogComments;
	}

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
	@Override
	public void updateBlogEntry(Long entryID, String updatedTitle, String updatedText, Date now) {
		EntityManager em = JPAEMProvider.getEntityManager();
		int affectedRows = em.createQuery(
				"update BlogEntry as b set b.title=:updatedTitle, b.text=:updatedText, b.lastModifiedAt=:now where b.id =:entryID")
				.setParameter("updatedTitle", updatedTitle).setParameter("updatedText", updatedText)
				.setParameter("now", now).setParameter("entryID", entryID).executeUpdate();
		em.close();
		if (affectedRows != 1) {
			throw new DAOException("Invalid number of affected rows, was: " + affectedRows);
		}
	}
}