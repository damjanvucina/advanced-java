package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

@WebServlet("/servleti/index.html")
public class OpcijeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Poll> pollList = DAOProvider.getDao().acquirePolls();
		
		HttpSession session = req.getSession();
		session.setAttribute("pollList", pollList);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeOpcije.jsp").forward(req, resp);
	}

}
