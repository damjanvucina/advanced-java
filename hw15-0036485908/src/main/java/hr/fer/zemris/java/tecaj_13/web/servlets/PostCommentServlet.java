package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import static hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet.SESSION_NICK_NAME;

@WebServlet("/servleti/postCommentServlet")
public class PostCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String INVALID_COMMENT = "invalidComment";
	public static final String INVALID_MAIL = "invalidMail";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message");
		
		Object objNick = req.getSession().getAttribute(SESSION_NICK_NAME);
		String commenterEmail = null;
		
		if (objNick != null) {
			BlogUser commenter = DAOProvider.getDAO().acquireUser((String) objNick);
			commenterEmail = commenter.getEmail();

		} else {
			commenterEmail = req.getParameter("commenterEmail");
		}

		if (message.isEmpty()) {
			req.setAttribute(INVALID_COMMENT, "invalid comment");
			req.getRequestDispatcher("/WEB-INF/pages/viewPost.jsp").forward(req, resp);
			return;
		}
		if (objNick == null && commenterEmail.isEmpty()) {
			req.setAttribute(INVALID_MAIL, "invalid comment");
			req.setAttribute("enteredMessage", message);
			req.setAttribute("blogEntryID", req.getAttribute("blogEntryID"));
			req.getRequestDispatcher("/WEB-INF/pages/viewPost.jsp").forward(req, resp);
			return;
		}

		Long blogID = Long.valueOf(req.getParameter("blogEntryID"));

		BlogComment comment = new BlogComment();
		comment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(blogID));
		comment.setMessage(message);
		comment.setPostedOn(new Date());
		comment.setUsersEMail(commenterEmail);

		DAOProvider.getDAO().performDatabaseInput(comment);

		resp.sendRedirect(req.getContextPath());
	}
}
