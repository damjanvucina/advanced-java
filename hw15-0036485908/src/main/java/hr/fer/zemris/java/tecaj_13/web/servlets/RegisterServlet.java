package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.Record;
import hr.fer.zemris.java.tecaj_13.model.FormularForm;

@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Record r = new Record();
		FormularForm f = new FormularForm();
		f.fromRecord(r);

		req.setAttribute("sheet", f);

		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}
