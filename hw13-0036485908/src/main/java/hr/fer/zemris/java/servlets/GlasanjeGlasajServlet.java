package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet class responsible for updating the current votes txt file based
 * on the user's input and redirecting to glasanje-rezultati.jsp page for
 * displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Updates the band-votes tracking file and redirects to glasanje-rezultati.jsp
	 * page for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path votingResults = Paths.get(fileName);

		if (!Files.exists(votingResults)) {
			errorOccurred("Results file does not exist.", req, resp);
		}
		if (!Files.isReadable(votingResults)) {
			errorOccurred("Results file is not readable.", req, resp);
		}

		String choiceIndex = req.getParameter("id");
		List<String> updatedFile = new ArrayList<>();

		for (String line : Files.readAllLines(votingResults)) {
			if (line.startsWith(choiceIndex)) {
				updatedFile.add(updateVotes(line));
			} else {
				updatedFile.add(line);
			}
		}

		Files.write(votingResults, updatedFile, Charset.defaultCharset());
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Updates the number of the votes of the band selected by the user.
	 *
	 * @param line
	 *            the line
	 * @return the string
	 */
	private String updateVotes(String line) {
		String[] elems = line.split("\t");
		elems[1] = String.valueOf(Integer.parseInt(elems[1]) + 1);
		return elems[0] + "\t" + elems[1];
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
