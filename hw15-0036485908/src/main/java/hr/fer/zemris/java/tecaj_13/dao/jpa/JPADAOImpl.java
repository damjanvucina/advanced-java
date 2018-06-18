package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser validateLogin(HttpServletRequest req, String nickName, String password) {
		EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("my.application.emf");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

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

		return user;
	}

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

	@Override
	public BlogUser createNewUser(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp,
			FormularForm f) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogUser user = new BlogUser();
		user.setFirstName(f.getFirstName());
		user.setLastName(f.getLastName());
		user.setNickName(f.getNickName());
		user.setPasswordHash(f.getPassword());
		user.setEmail(f.getEmail());
		user.setPasswordHash(toSHA1(f.getPassword()));

		em.persist(user);

		em.getTransaction().commit();
		em.close();

		return user;
	}

	@Override
	public List<BlogUser> acquireRegisteredAuthors(EntityManagerFactory emf, HttpServletRequest req,
			HttpServletResponse resp) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<BlogUser> registeredAuthors = null;
		try {
			registeredAuthors = em.createQuery("select b from BlogUser as b", BlogUser.class).getResultList();
		} catch (NoResultException ignorable) {
		}

		return registeredAuthors;
	}

	@Override
	public List<BlogEntry> acquireUserEntries(EntityManagerFactory emf, HttpServletRequest req,
			HttpServletResponse resp, Long authorID) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<BlogEntry> userEntries = null;

		try {
			em.createQuery("select b from BlogEntry as b where b.creator.id=:creatorID", BlogEntry.class)
					.setParameter("creatorID", authorID).getResultList();
		} catch (NoResultException ignorable) {
		}

		return userEntries;
	}

	@Override
	public Long acquireUserID(EntityManagerFactory emf, String nickName) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Long userID = null;

		try {
			userID = em.createQuery("select b.id from BlogUser as b where b.nickName=:nickName", Long.class)
					.setParameter("nickName", nickName).getSingleResult();
		} catch (NoResultException ignorable) {
		}

		return userID;
	}

}