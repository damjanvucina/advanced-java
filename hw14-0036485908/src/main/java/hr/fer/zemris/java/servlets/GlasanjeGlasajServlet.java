package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * The servlet class responsible for updating the current votes txt file based
 * on the user's input and redirecting to glasanje-rezultati.jsp page for
 * displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Updates the pollOption-votesNumber tracking file and redirects to glasanje-rezultati.jsp
	 * page for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long optionID = Long.valueOf(req.getParameter("id"));
		Long pollID = DAOProvider.getDao().identifyPoll(optionID);
		
		DAOProvider.getDao().performVoting(optionID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}
}
