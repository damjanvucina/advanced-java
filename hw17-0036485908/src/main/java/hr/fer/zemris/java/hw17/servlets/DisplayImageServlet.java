package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.models.Photo;

import static hr.fer.zemris.java.hw17.servlets.InitializationServlet.PHOTOS;
@WebServlet("/servlets/display-image")
public class DisplayImageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final String PARAMETER ="arg";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Photo> photos = (Map<String, Photo>) req.getServletContext().getAttribute(PHOTOS);
		String name = req.getParameter(PARAMETER);
		
		Photo selectedPhoto = photos.get(name);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(selectedPhoto);

		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

}
