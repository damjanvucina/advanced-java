package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * The servlet class used for updating BlogEntries on the author's request.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/updatePost")
public class UpdatePostServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * POST request. Acquires new blog text and title and updates the specified
	 * entry with these values.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long entryID = Long.valueOf((String) req.getParameter("entryID"));
		String updatedTitle = (String) req.getParameter("title");
		String updatedText = (String) req.getParameter("text");

		DAOProvider.getDAO().updateBlogEntry(entryID, updatedTitle, updatedText, new Date());
		resp.sendRedirect(req.getContextPath());
	}
}
