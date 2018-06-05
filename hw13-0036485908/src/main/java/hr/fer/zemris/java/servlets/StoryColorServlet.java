package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The servlet class responsible for generating a random color to be set as font
 * color of the text on the funny.jsp page
 * 
 * @author Damjan Vučina
 */
@WebServlet("/funny")
public class StoryColorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The random. */
	Random random = new Random();

	/**
	 * Generates a random color and sets its value as session attribute so it can be
	 * obtained from the funny.jsp page
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = generateRandomColor();

		HttpSession session = req.getSession();
		session.setAttribute("color", "color:" + color.toString());

		req.getRequestDispatcher("WEB-INF/stories/funny.jsp").forward(req, resp);
	}

	/**
	 * Generates random color and formats it accordingly.
	 *
	 * @return the string representation of the color
	 */
	private String generateRandomColor() {
		int nextInt = random.nextInt(256 * 256 * 256);
		return String.format("%06x", nextInt);
	}
}
