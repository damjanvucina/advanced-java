package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;
import hr.fer.zemris.java.tecaj_13.model.Record;

import static hr.fer.zemris.java.tecaj_13.model.FormularForm.NICK_NAME_ERROR;

/**
 * The servlet class used for processing the registration action by validating
 * the registration form and registering new users.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/validateRegistration")
public class ValidateRegistrationServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Processes the user's registration.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * POST request. Processes the user's registration.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	/**
	 * Validates the registration form and registers new users if the form is in
	 * compliance with the specified format.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");
		if (!"Register".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		}

		FormularForm f = new FormularForm();
		f.fromHttpRequest(req);
		f.validate();

		if (f.hasErrors()) {
			req.setAttribute("sheet", f);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		Record r = new Record();
		f.toRecord(r);

		EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("my.application.emf");

		processRegister(emf, req, resp, f);
	}

	/**
	 * Registers new user after the registration form has been validated.
	 *
	 * @param emf
	 *            the emf
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param f
	 *            the f
	 */
	private void processRegister(EntityManagerFactory emf, HttpServletRequest req, HttpServletResponse resp,
			FormularForm f) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogUser user = null;
		try {
			user = em.createQuery("select b from BlogUser as b where b.nickName=:nickName", BlogUser.class)
					.setParameter("nickName", f.getNickName()).getSingleResult();
		} catch (NoResultException ignorable) {
		}

		if (user != null) {
			f.getErrors().put(NICK_NAME_ERROR, "Nick name already exists. Please choose different one.");
			req.setAttribute("sheet", f);
			try {
				req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			em.close();

		} else {
			BlogUser newUser = DAOProvider.getDAO().createNewUser(req, resp, f);
			try {
				LoginServlet.setSessionAttributes(req, newUser);
				resp.sendRedirect("./main");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
