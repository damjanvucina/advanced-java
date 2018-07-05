package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import hr.fer.zemris.java.models.Photo;

import static hr.fer.zemris.java.hw17.servlets.InitializationListener.PHOTOS;

@WebServlet("/servlets/get-tags")
public class GetTagsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Photo> photos =((Map<String, Photo>) req.getServletContext().getAttribute(PHOTOS));
		Set<String> tags = new HashSet<>();
		photos.forEach((key, value) -> tags.addAll(value.getTags()));
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);

		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
