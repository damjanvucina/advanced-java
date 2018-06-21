package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * The servlet class used for loading up the list of currently registered
 * authors.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Loads up the list of currently registered authors.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> registeredAuthors = DAOProvider.getDAO().acquireRegisteredAuthors(req, resp);
		req.getSession().setAttribute("registeredAuthors", registeredAuthors);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
