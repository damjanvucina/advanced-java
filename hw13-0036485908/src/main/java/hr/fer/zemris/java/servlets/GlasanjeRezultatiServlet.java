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
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The servlet class responsible for creating a map containing the bands an
 * their votes. This map is to be used by the glasanjeRez.jsp file for
 * displaying purposes. This class also generates a map to be used for
 * displaying the links for the mos popular bands.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a band-votes and band-link mappings to be used by the glasanjeRez.jsp
	 * file for displaying purposes.
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

		Map<String, String> references = acquireReferences(votingFile, sortedMap);

		HttpSession session = req.getSession();
		session.setAttribute("results", sortedMap);

		req.getServletContext().setAttribute("results", sortedMap);
		req.getServletContext().setAttribute("references", references);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Acquires references to bands from the base txt file representing a database.
	 *
	 * @param votingFile
	 *            the voting file
	 * @param sortedMap
	 *            the sorted map
	 * @return the map
	 */
	private Map<String, String> acquireReferences(List<String> votingFile, Map<String, Integer> sortedMap) {
		int maxVotes = 0;
		Map<String, String> references = new HashMap<>();

		for (Entry<String, Integer> entry : sortedMap.entrySet()) {
			if (entry.getValue() > maxVotes) {
				maxVotes = entry.getValue();
			}
		}

		for (Entry<String, Integer> entry : sortedMap.entrySet()) {
			if (entry.getValue() == maxVotes) {
				references.put(entry.getKey(), "UNDEFINED");
			}
		}

		for (String line : votingFile) {
			String[] elems = line.split("\t");
			if (references.containsKey(elems[1])) {
				references.put(elems[1], elems[2]);
			}
		}

		return references;
	}

	/**
	 * Extracts band name based on the id of the band from the txt file representing
	 * a database.
	 *
	 * @param bandID
	 *            the band ID
	 * @param votingFile
	 *            the voting file
	 * @return the band's name
	 */
	private String extractBandName(String bandID, List<String> votingFile) {
		for (String line : votingFile) {

			String elems[] = line.split("\t");
			if (elems[0].equals(bandID)) {
				return elems[1];
			}
		}
		return bandID;
	}

	/**
	 * Helper method used for sorting a hashmap by its Integer value.
	 *
	 * @param map
	 *            the map
	 * @return the sorted map
	 */
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
