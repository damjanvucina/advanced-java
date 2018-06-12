package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	 * Updates the band-votes tracking file and redirects to glasanje-rezultati.jsp
	 * page for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long optionID = Long.valueOf(req.getParameter("id"));
		Long pollID = DAOProvider.getDao().identifyPoll(optionID);
		
		DAOProvider.getDao().performVoting(optionID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}


	/**
	 * Signals that an error occurred by redirecting to a error page with the error
	 * message.
	 *
	 * @param errorMessage
	 *            the error message
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 */
	private void errorOccurred(String errorMessage, HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/XLSGeneratorError.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
