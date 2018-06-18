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

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;
import hr.fer.zemris.java.tecaj_13.model.Record;

import static hr.fer.zemris.java.tecaj_13.model.FormularForm.NICK_NAME_ERROR;

@WebServlet("/servleti/validateRegistration")
public class ValidateRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

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
			BlogUser newUser = DAOProvider.getDAO().createNewUser(emf, req, resp, f);
			try {
				LoginServlet.setSessionAttributes(req, newUser);
				resp.sendRedirect("./main");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
