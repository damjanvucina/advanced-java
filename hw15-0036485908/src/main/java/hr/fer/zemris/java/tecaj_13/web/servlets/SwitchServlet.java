package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_NICK_NAME;
import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_ID;

@WebServlet("/servleti/author/*")
public class SwitchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String INVALID_POST = "invalidPost";
	public static final String ENTERED_TITLE = "enteredTitle";
	public static final String ENTERED_TEXT = "enteredText";

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

	private void processEdit(HttpServletRequest req, HttpServletResponse resp, String indicator) throws ServletException, IOException {
		Long entryID = Long.valueOf(req.getParameter("id"));
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
		req.setAttribute("enteredTitle", entry.getTitle());
		req.setAttribute("enteredText", entry.getText());
		req.setAttribute("enteredID", entryID);
		
		req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
	}

	private void processViewPost(HttpServletRequest req, HttpServletResponse resp, String indicator)
			throws ServletException, IOException {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(indicator));
		List<BlogComment> comments = DAOProvider.getDAO().acquireBlogComments(req, resp, Long.valueOf(indicator));

		req.setAttribute("blogEntry", entry);
		req.setAttribute("comments", comments);

		req.getRequestDispatcher("/WEB-INF/pages/viewPost.jsp").forward(req, resp);
	}

	private boolean isSelectedPost(String indicator) {
		try {
			Long.parseLong(indicator);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

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

	private void setUpEntries(HttpServletRequest req, HttpServletResponse resp, Long authorID) {

		BlogUser user = DAOProvider.getDAO().acquireUser((String) req.getSession().getAttribute(SESSION_NICK_NAME));

		List<BlogEntry> userEntries = DAOProvider.getDAO().acquireUserEntries(req, resp, authorID);
		req.setAttribute("userEntries", userEntries);
	}

	private void processNew(HttpServletRequest req, HttpServletResponse resp, String authorNick)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(req, resp);
	}
}
