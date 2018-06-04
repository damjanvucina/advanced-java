package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/funny")
public class StoryColorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Random random = new Random();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = generateRandomColor();

		HttpSession session = req.getSession();
		session.setAttribute("color", "color:" + color.toString());

		req.getRequestDispatcher("WEB-INF/stories/funny.jsp").forward(req, resp);
	}

	private String generateRandomColor() {
		int nextInt = random.nextInt(256 * 256 * 256);
		return String.format("%06x", nextInt);
	}
}
