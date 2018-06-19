package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_NICK_NAME;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntityManagerFactory emf = (EntityManagerFactory) req.getServletContext().getAttribute("my.application.emf");

		String pathInfo = req.getPathInfo();
		String authorNick = pathInfo.substring(1);

		Long authorID = DAOProvider.getDAO().acquireUserID(emf, authorNick);

		if (authorNick == null) {
			req.setAttribute("errorMessage", "Invalid author nickname");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		List<BlogEntry> userEntries = DAOProvider.getDAO().acquireUserEntries(emf, req, resp, authorID);
		req.setAttribute("userEntries", userEntries);
		req.setAttribute("authorNick", authorNick);

		if (authorNick.equals(req.getSession().getAttribute(SESSION_NICK_NAME))) {
			req.setAttribute("owner", true);
		}

		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}
}
