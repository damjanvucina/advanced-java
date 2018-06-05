package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

/**
 * The servlet class responsible for loading the bands information from the txt
 * file representing a database, storing this information in session variable
 * and redirecting to glasanjeIndex.jsp for displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Loads bands information from the txt file representing a database, stores
	 * this information and redirects to glasanjeIndex.jsp for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path votingOptions = Paths.get(fileName);

		if (!Files.exists(votingOptions)) {
			errorOccurred("Voting file does not exist.", req, resp);
		}
		if (!Files.isReadable(votingOptions)) {
			errorOccurred("Voting is not readable.", req, resp);
		}

		List<String> votingFile = new ArrayList<>();
		Map<String, String> bands = new HashMap<>();

		votingFile = Files.readAllLines(votingOptions);

		for (String line : votingFile) {
			bands.put(line.split("\t")[0], line.split("\t")[1]);
		}

		HttpSession session = req.getSession();
		session.setAttribute("bands", bands);

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
