package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The servlet class responsible for loading the polling options' information
 * from the database, storing this information in session variable and
 * redirecting to glasanjeIndex.jsp for displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Loads polling options' information from the database, stores this information
	 * and redirects to glasanjeIndex.jsp for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.valueOf(req.getParameter("pollID"));

		List<PollOption> pollOptions = new ArrayList<>();
		pollOptions = DAOProvider.getDao().acquirePollOptions(pollID);

		req.setAttribute("pollOptions", pollOptions);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
