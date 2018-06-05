package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The class that represents a servlet object responsible for altering the
 * application's background color based on the user's request. Colors supported:
 * white, red, green, cyan.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant that defines the selected color. */
	private static final String COLOR_ATTRIBUTE = "pickedBgCol";

	/** The Constant that defines the default color */
	private static final String DEFAULT_COLOR = "white";

	/**
	 * Identifies and validates the color user has requested, sets session attribute
	 * with the specified color value and redirects back to the invoking page.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pickedColor = req.getParameter("color");
		if (pickedColor != null) {
			pickedColor = pickedColor.trim();
		}
		if (!isSupportedColor(pickedColor)) {
			pickedColor = DEFAULT_COLOR;
		}

		HttpSession session = req.getSession();
		session.setAttribute(COLOR_ATTRIBUTE, pickedColor);

		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}

	/**
	 * Checks if the chosen color is in fact supported. Supported colors are: white,
	 * red, green and cyan.
	 *
	 * @param pickedColor
	 *            the picked color
	 * @return true, if the chosen color is supported
	 */
	private boolean isSupportedColor(String pickedColor) {
		return pickedColor.equals("red") || pickedColor.equals("green") || pickedColor.equals("white")
				|| pickedColor.equals("cyan");

	}

}
