package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * The servlet class responsible for creating a map containing the polling
 * options an their votes. This map is to be used by the glasanjeRez.jsp file
 * for displaying purposes. This class also generates a map to be used for
 * displaying the links for the most popular polling options.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a polling option-number of votes and polling option-link mappings to
	 * be used by the glasanjeRez.jsp file for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.valueOf(req.getParameter("pollID"));

		Map<String, Long> sortedMap = new LinkedHashMap<>();
		sortedMap = DAOProvider.getDao().acquirePollResults(pollID);// ime banda broj glasova

		Long votesCount = sortedMap.entrySet().iterator().next().getValue();
		Map<String, String> references = DAOProvider.getDao().acquireReferences(pollID, votesCount);

		HttpSession session = req.getSession();
		session.setAttribute("results", sortedMap);

		req.getServletContext().setAttribute("results", sortedMap);
		req.getServletContext().setAttribute("references", references);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
