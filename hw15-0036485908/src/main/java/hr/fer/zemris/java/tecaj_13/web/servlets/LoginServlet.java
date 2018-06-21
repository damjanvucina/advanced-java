package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * The servlet class that is responsible for validating user's credentials as
 * well as setting up session attributes.
 * 
 * @author Damjan Vučina
 */
@WebServlet(name = "loginSevlet", urlPatterns = "/servleti/login")
public class LoginServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant SESSION_ID. */
	public static final String SESSION_ID = "current.user.id";

	/** The Constant SESSION_FIRST_NAME. */
	public static final String SESSION_FIRST_NAME = "current.user.fn";

	/** The Constant SESSION_LAST_NAME. */
	public static final String SESSION_LAST_NAME = "current.user.ln";

	/** The Constant SESSION_NICK_NAME. */
	public static final String SESSION_NICK_NAME = "current.user.nick";

	/** The Constant SESSION_INVALID_LOGIN. */
	public static final String SESSION_INVALID_LOGIN = "invalid.login";

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogin(req, resp);
	}

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * POST request.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogin(req, resp);
	}

	/**
	 * Helper method used for validating user's credentials
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 */
	private void processLogin(HttpServletRequest req, HttpServletResponse resp) {
		String nickName = req.getParameter("nickName");
		String password = req.getParameter("password");

		BlogUser user = DAOProvider.getDAO().validateLogin(req, nickName, password);

		if (user != null) {
			setSessionAttributes(req, user);
			try {
				resp.sendRedirect(req.getContextPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			req.setAttribute(SESSION_INVALID_LOGIN, "Invalid nickname or password.");
			try {
				req.setAttribute("invalidNickname", nickName);
				List<BlogUser> registeredAuthors = DAOProvider.getDAO().acquireRegisteredAuthors(req, resp);
				req.setAttribute("registeredAuthors", registeredAuthors);
				req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Helper method used for setting up the session attributes.
	 *
	 * @param req
	 *            the req
	 * @param user
	 *            the user
	 */
	public static void setSessionAttributes(HttpServletRequest req, BlogUser user) {
		req.getSession().setAttribute(SESSION_ID, user.getId());
		req.getSession().setAttribute(SESSION_FIRST_NAME, user.getFirstName());
		req.getSession().setAttribute(SESSION_LAST_NAME, user.getLastName());
		req.getSession().setAttribute(SESSION_NICK_NAME, user.getNickName());
	}

}
