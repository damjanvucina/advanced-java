package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet(name = "loginSevlet", urlPatterns = "/servleti/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SESSION_ID = "current.user.id";
	public static final String SESSION_FIRST_NAME = "current.user.fn";
	public static final String SESSION_LAST_NAME = "current.user.ln";
	public static final String SESSION_NICK_NAME = "current.user.nick";
	public static final String SESSION_INVALID_LOGIN = "invalid.login";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogin(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogin(req, resp);
	}

	private void processLogin(HttpServletRequest req, HttpServletResponse resp) {
		String nickName = req.getParameter("nickName");
		String password = req.getParameter("password");

		BlogUser user = DAOProvider.getDAO().validateLogin(req, nickName, password);

		if (user != null) {
			setSessionAttributes(req, user);
			try {
				resp.sendRedirect("../main");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			req.getSession().setAttribute(SESSION_INVALID_LOGIN, "Invalid nickname or password.");
			try {
				req.setAttribute("invalidNickname", nickName);
				req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setSessionAttributes(HttpServletRequest req, BlogUser user) {
		req.getSession().setAttribute(SESSION_ID, user.getId());
		req.getSession().setAttribute(SESSION_FIRST_NAME, user.getFirstName());
		req.getSession().setAttribute(SESSION_LAST_NAME, user.getLastName());
		req.getSession().setAttribute(SESSION_NICK_NAME, user.getNickName());
	}

}
