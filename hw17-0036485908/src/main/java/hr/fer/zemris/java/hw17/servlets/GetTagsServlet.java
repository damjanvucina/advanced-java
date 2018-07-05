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

import hr.fer.zemris.java.hw17.rest.PhotoDB;
import hr.fer.zemris.java.models.Photo;

/**
 * The servlet that is used for acquiring all the tags from the dummy database
 * txt file.
 * 
 * @author Damjan Vučina
 */

// NOTICE: This class is NOT used by default since TagsJSON class
// provides the user with the same functionality and does so offering RESTful
// Web service support
@WebServlet("/servlets/get-tags")
public class GetTagsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Gets the all the tags from the dummy database txt file.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Photo> photos = PhotoDB.getPhotos();
		Set<String> tags = new HashSet<>();
		
		photos.forEach((key, value) -> tags.addAll(value.getTags()));

		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);

		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
