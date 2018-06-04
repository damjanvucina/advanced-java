package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String COLOR_ATTRIBUTE = "pickedBgCol";
	private static final String DEFAULT_COLOR = "white";

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

	private boolean isSupportedColor(String pickedColor) {
		return pickedColor.equals("red") ||
			   pickedColor.equals("green") || 
			   pickedColor.equals("white") ||
			   pickedColor.equals("cyan");

	}

}
