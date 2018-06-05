package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
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
		String bandNames = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path votingOptions = Paths.get(bandNames);
		Map<String, String> bands = new HashMap<>();

		List<String> votingFile = Files.readAllLines(votingOptions);

		for (String line : votingFile) {
			bands.put(extractBandName(line.split("\t")[0], votingFile), line.split("\t")[1]);
		}

		Map<String, Integer> map = new HashMap<>();
		List<String> results = new ArrayList<>();

		results = Files.readAllLines(votingResults);

		for (String line : results) {
			map.put(extractBandName(line.split("\t")[0], votingFile), Integer.parseInt(line.split("\t")[1]));
		}

		Map<String, Integer> sortedMap = sortByValue(map);

		HttpSession session = req.getSession();
		session.setAttribute("results", sortedMap);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	private String extractBandName(String bandID, List<String> votingFile) {
		for (String line : votingFile) {

			String elems[] = line.split("\t");
			if (elems[0].equals(bandID)) {
				return elems[1];
			}
		}
		return bandID;
	}

	// algorithm used available at
	// https://www.mkyong.com/java/how-to-sort-a-map-in-java/
	private Map<String, Integer> sortByValue(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
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
