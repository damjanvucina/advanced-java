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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message");

		if (message.isEmpty()) {
			req.setAttribute(INVALID_COMMENT, "invalid comment");
			req.getRequestDispatcher("/WEB-INF/pages/viewPost.jsp").forward(req, resp);
			return;
		}
		
		Long blogID = Long.valueOf(req.getParameter("blogEntryID"));
		BlogUser commenter = DAOProvider.getDAO().acquireUser((String) req.getSession().getAttribute(SESSION_NICK_NAME));
		
		BlogComment comment =  new BlogComment();
		comment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(blogID));
		comment.setMessage(message);
		comment.setPostedOn(new Date());
		comment.setUsersEMail(commenter.getEmail());
		
		DAOProvider.getDAO().performDatabaseInput(comment);
		
		resp.sendRedirect(req.getContextPath());
	}
}
