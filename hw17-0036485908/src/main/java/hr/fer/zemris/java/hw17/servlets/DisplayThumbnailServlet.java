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

@WebServlet("/servlets/display-thumbnail")
public class DisplayThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAMETER = "name";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter(PARAMETER);

		resp.setContentType("image/jpeg");

		Path imgPath = Paths.get(getServletContext().getRealPath(THUMBNAILS_FILE)).resolve(name);
		BufferedImage bi = ImageIO.read(imgPath.toFile());
		OutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	}

}
