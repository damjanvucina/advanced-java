package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static hr.fer.zemris.java.hw17.servlets.CreateThumbnailsServlet.THUMBNAILS_FILE;
import static hr.fer.zemris.java.hw17.servlets.CreateThumbnailsServlet.BASE_RESOURCES;

/**
 * The class that is used for displaying a requested image, let it be full sized
 * image or a thumbnail.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servlets/display-image")
public class DisplayImageServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant IMAGE_NAME. */
	private static final String IMAGE_NAME = "name";

	/** The Constant IMAGE_SIZE. */
	private static final String IMAGE_SIZE = "size";

	/** The Constant THUMBNAIL. */
	private static final String THUMBNAIL = "thumbnail";

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Displays the designated image in full-size or a thumbnail size,
	 * depending on the request parameter.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter(IMAGE_NAME);
		String imageSize = req.getParameter(IMAGE_SIZE);

		resp.setContentType("image/jpeg");

		Path imgPath = null;
		if (imageSize.equals(THUMBNAIL)) {
			imgPath = Paths.get(getServletContext().getRealPath(THUMBNAILS_FILE)).resolve(name);
		} else {
			imgPath = Paths.get(getServletContext().getRealPath(BASE_RESOURCES)).resolve(name);
		}

		BufferedImage bi = ImageIO.read(imgPath.toFile());
		OutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}

}
