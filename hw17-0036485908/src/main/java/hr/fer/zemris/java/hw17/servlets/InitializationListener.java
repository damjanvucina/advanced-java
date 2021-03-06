package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw17.rest.PhotoDB;
import hr.fer.zemris.java.models.Photo;

/**
 * The listener class used for creating mappings for the images stored in the
 * dummy database txt file.
 * 
 * @author Damjan Vučina
 */
@WebListener
public class InitializationListener implements ServletContextListener {

	/** The Constant RESOURCE_FILE. */
	public static final String RESOURCE_FILE = "/WEB-INF/opisnik.txt";

	/** The Constant PHOTOS. */
	public static final String PHOTOS = "photos";

	/**
	 * Receives notification that the web application initialization process is
	 * starting. Creates mappings for the images stored in the dummy database txt
	 * file.
	 */

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path path = Paths.get(sce.getServletContext().getRealPath(RESOURCE_FILE));
		List<String> lines = null;

		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Photo> photos = new HashMap<>();
		for (int i = 0, size = lines.size(); i < size; i += 3) {
			photos.put(lines.get(i), new Photo(lines.get(i), lines.get(i + 1), lines.get(i + 2)));
		}

		PhotoDB.setPhotos(photos);
	}

	/**
	 * Receives notification that the ServletContext is about to be shut down.
	 * NOTICE: Not implemented here.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
