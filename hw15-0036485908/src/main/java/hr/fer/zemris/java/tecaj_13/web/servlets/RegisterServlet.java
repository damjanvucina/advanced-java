package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.Record;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

/**
 * The servlet class used for acquiring the registration form for the new users.
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Acquires new FormularForm used for registrating new users.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Record r = new Record();
		FormularForm f = new FormularForm();
		f.fromRecord(r);

		req.setAttribute("sheet", f);

		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}
