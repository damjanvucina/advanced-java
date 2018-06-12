package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The servlet class responsible for loading the bands information from the txt
 * file representing a database, storing this information in session variable
 * and redirecting to glasanjeIndex.jsp for displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Loads bands information from the txt file representing a database, stores
	 * this information and redirects to glasanjeIndex.jsp for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.valueOf(req.getParameter("pollID"));
		
		List<PollOption> pollOptions = new ArrayList<>();
		pollOptions = DAOProvider.getDao().acquirePollOptions(pollID);
		
		HttpSession session = req.getSession();
		session.setAttribute("pollOptions", pollOptions);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
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
