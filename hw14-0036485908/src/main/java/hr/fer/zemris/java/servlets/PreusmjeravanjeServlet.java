package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet class responsible for redirecting /index.html URL to
 * /servleti/index.html URL in the client's browser.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/index.html")
public class PreusmjeravanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Redirects /index.html URL to /servleti/index.html URL in the client's
	 * browser.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./servleti/index.html");
	}
}
