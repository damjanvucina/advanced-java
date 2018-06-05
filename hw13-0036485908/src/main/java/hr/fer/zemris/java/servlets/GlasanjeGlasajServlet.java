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

@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	private String updateVotes(String line) {
		String[] elems = line.split("\t");
		elems[1] = String.valueOf(Integer.parseInt(elems[1]) + 1);
		return elems[0] + "\t" + elems[1];
	}

	private void errorOccurred(String errorMessage, HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("errorMessage", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/XLSGeneratorError.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
