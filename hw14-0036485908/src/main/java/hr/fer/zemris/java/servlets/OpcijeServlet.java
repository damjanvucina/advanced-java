package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * The servlet class responsible for loading available polls from the database
 * and forwarding to the designated jsp page for displaying purposes.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/index.html")
public class OpcijeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Loads available polls from the database and forwards to the designated jsp
	 * page for displaying purposes.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> pollList = DAOProvider.getDao().acquirePolls();

		req.setAttribute("pollList", pollList);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeOpcije.jsp").forward(req, resp);
	}

}
