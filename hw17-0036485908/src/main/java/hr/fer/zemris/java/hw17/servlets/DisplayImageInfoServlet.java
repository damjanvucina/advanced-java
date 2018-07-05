package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.rest.PhotoDB;
import hr.fer.zemris.java.models.Photo;

/**
 * The class that is used for the acquiring the details of a designated image.
 * Image is defined by its name, description and tags.
 */
// NOTICE: This class is NOT used by default since ImageInfoJSON class
// provides the user with the same functionality and does so offering RESTful
// Web service support
@WebServlet("/servlets/display-image-info")
public class DisplayImageInfoServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant PARAMETER. */
	private static final String PARAMETER = "arg";

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Gets the designated image details.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Photo> photos = PhotoDB.getPhotos();
		String name = req.getParameter(PARAMETER);

		Photo selectedPhoto = photos.get(name);

		Gson gson = new Gson();
		String jsonText = gson.toJson(selectedPhoto);

		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
