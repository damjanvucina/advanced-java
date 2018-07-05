package hr.fer.zemris.java.hw17.servlets;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.rest.PhotoDB;
import hr.fer.zemris.java.models.Photo;

/**
 * The servlet class that is used for creating thumbnails for the designated
 * images had they not previously been generated and stored.
 * 
 * @author Damjan Vučina
 */
@WebServlet("/servlets/get-thumbnails")
public class CreateThumbnailsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TAG. */
	public static final String TAG = "tag";

	/** The Constant THUMBNAILS_FILE. */
	public static final String THUMBNAILS_FILE = "/WEB-INF/thumbnails";

	/** The Constant BASE_RESOURCES. */
	public static final String BASE_RESOURCES = "/WEB-INF/slike";

	/** The Constant THUMBNAIL_DIMENSION. */
	private static final int THUMBNAIL_DIMENSION = 150;

	/**
	 * Called by the server (via the service method) to allow a servlet to handle a
	 * GET request. Generates and stores a thumbnail for the images defined by the
	 * corresponding tag.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter(TAG);
		Map<String, Photo> photos = PhotoDB.getPhotos();
		
		Path thumbnailsFolder = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_FILE));
		if (Files.notExists(thumbnailsFolder)) {
			Files.createDirectory(thumbnailsFolder);
		}

		//@formatter:off
		List<String> selectedPhotosNames =photos.values()
										  .stream()
										  .filter(photo -> photo.getTags().contains(tag))
										  .map(Photo::getName)
										  .collect(Collectors.toList());
		
		//@formatter:on
		for (String photoName : selectedPhotosNames) {
			Path thumbPath = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_FILE)).resolve(photoName);

			if (!Files.exists(thumbPath)) {
				Path imgPath = Paths.get(req.getServletContext().getRealPath(BASE_RESOURCES)).resolve(photoName);
				BufferedImage img = ImageIO.read(imgPath.toFile());
				BufferedImage scaled = scale(img);
				ImageIO.write(scaled, "jpg", new File(String.valueOf(thumbPath)));
			}
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(selectedPhotosNames);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}

	/**
	 * Helper method used for scaling the image.
	 *
	 * @param source
	 *            the source
	 * @return the buffered image
	 */
	private BufferedImage scale(BufferedImage source) {
		int dim = THUMBNAIL_DIMENSION;
		BufferedImage bi = getCompatibleImage(dim, dim);
		Graphics2D g2d = bi.createGraphics();
		double xScale = (double) dim / source.getWidth();
		double yScale = (double) dim / source.getHeight();
		AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
		g2d.drawRenderedImage(source, at);
		g2d.dispose();
		return bi;
	}

	/**
	 * Helper method used for creating a compatible image.
	 *
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @return the compatible image
	 */
	private BufferedImage getCompatibleImage(int w, int h) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		return image;
	}

}
