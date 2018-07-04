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

import static hr.fer.zemris.java.hw17.servlets.GetThumbnailsServlet.THUMBNAILS_FILE;
import static hr.fer.zemris.java.hw17.servlets.GetThumbnailsServlet.BASE_RESOURCES;

@WebServlet("/servlets/display-thumbnail")
public class DisplayThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_NAME = "name";
	private static final String IMAGE_SIZE = "size";
	private static final String THUMBNAIL = "thumbnail";

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
