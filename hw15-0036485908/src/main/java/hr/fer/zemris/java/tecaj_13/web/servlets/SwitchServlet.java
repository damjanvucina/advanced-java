package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_NICK_NAME;
import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_ID;

/**
 * The servlet class that serves the purpose of redirecting the flow of the
 * application depending on the last parts of the selected URL. It provides user
 * with methods for creating a new blog entry, editing an existign blog entry as
 * well as accessing user's profile pages.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servleti/author/*")
public class SwitchServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant INVALID_POST. */
	public static final String INVALID_POST = "invalidPost";

	/** The Constant ENTERED_TITLE. */
	public static final String ENTERED_TITLE = "enteredTitle";

	/** The Constant ENTERED_TEXT. */
	public static final String ENTERED_TEXT = "enteredText";

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * POST request. Method used for creating a new Blog Entry.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("text");

		if (title.isEmpty() || text.isEmpty()) {
			req.setAttribute(INVALID_POST, "Invalid nickname or password.");
			req.setAttribute(ENTERED_TITLE, title);
			req.setAttribute(ENTERED_TEXT, text);

			req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);

		} else {

			String creatorNick = (String) req.getSession().getAttribute(SESSION_NICK_NAME);

			BlogEntry entry = new BlogEntry();
			entry.setTitle(title);
			entry.setText(text);
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(entry.getCreatedAt());
			entry.setCreator(DAOProvider.getDAO().acquireUser(creatorNick));

			DAOProvider.getDAO().performDatabaseInput(entry);
			setUpEntries(req, resp, (Long) req.getSession().getAttribute(SESSION_ID));
			resp.sendRedirect(req.getContextPath());
		}
	}

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Redirects the flow of the application depending on the last
	 * parts of the selected url. Delegates to helper methods for creating a new
	 * blog entry, editing an existign blog entry as well as accessing user's
	 * profile pages.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		int indexOfSeparator = pathInfo.lastIndexOf("/");
		String indicator = pathInfo.substring(indexOfSeparator + 1);

		if (indicator.equals("new")) {
			processNew(req, resp, indicator);

		} else if (indicator.equals("edit")) {
			processEdit(req, resp, indicator);

		} else if (isSelectedPost(indicator)) {
			processViewPost(req, resp, indicator);

		} else {
			processAuthor(req, resp, indicator);
		}
	}

	/**
	 * Edits an existing blog entry.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param indicator
	 *            the indicator
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processEdit(HttpServletRequest req, HttpServletResponse resp, String indicator)
			throws ServletException, IOException {
		Long entryID = Long.valueOf(req.getParameter("id"));

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
		req.setAttribute("enteredTitle", entry.getTitle());
		req.setAttribute("enteredText", entry.getText());
		req.setAttribute("enteredID", entryID);
		req.setAttribute("creator", entry.getCreator());

		req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
	}

	/**
	 * Prepares the data for viewing a specific BlogEntry as well as its comments.
	 *
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @param indicator
	 *            the indicator
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processViewPost(HttpServletRequest req, HttpServletResponse resp, String indicator)
			throws ServletException, IOException {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(indicator));
		List<BlogComment> comments = DAOProvider.getDAO().acquireBlogComments(req, resp, Long.valueOf(indicator));

		req.setAttribute("blogEntry", entry);
		req.setAttribute("comments", comments);

		req.getRequestDispatcher("/WEB-INF/pages/viewPost.jsp").forward(req, resp);
	}

	/**
	 * Checks if the last part of the URL is a Long number, i.e. represents an id of
	 * a BlogEntry.
	 *
	 * @param indicator
	 *            the indicator
	 * @return true, if is selected post
	 */
	private boolean isSelectedPost(String indicator) {
		try {
			Long.parseLong(indicator);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Prepares the data for viewing a user's profile page containing all
	 * BlogEntries made by him.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param authorNick
	 *            the author nick
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processAuthor(HttpServletRequest req, HttpServletResponse resp, String authorNick)
			throws ServletException, IOException {

		Long authorID = DAOProvider.getDAO().acquireUserID(authorNick);

		if (authorNick == null) {
			req.setAttribute("errorMessage", "Invalid author nickname");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		setUpEntries(req, resp, authorID);

		req.setAttribute("authorNick", authorNick);

		if (authorNick.equals(req.getSession().getAttribute(SESSION_NICK_NAME))) {
			req.setAttribute("owner", true);
		}

		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}

	/**
	 * Sets the up entries.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param authorID
	 *            the author ID
	 */
	private void setUpEntries(HttpServletRequest req, HttpServletResponse resp, Long authorID) {
		List<BlogEntry> userEntries = DAOProvider.getDAO().acquireUserEntries(req, resp, authorID);
		req.setAttribute("userEntries", userEntries);
	}

	/**
	 * Forwards application flow to the jsp page for creating a new BlogEntry.
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @param authorNick
	 *            the author nick
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processNew(HttpServletRequest req, HttpServletResponse resp, String authorNick)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		pathInfo = pathInfo.substring(1);
		pathInfo = pathInfo.substring(0, pathInfo.indexOf("/"));

		req.setAttribute("ownerNick", pathInfo);
		req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);
	}
}
