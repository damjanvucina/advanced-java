package hr.fer.zemris.java.hw17.rest;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.models.Photo;

/**
 * The class used for acquiring the details of a designated image. Image is
 * defined by its name, description and tags. This class is built upon Jersey
 * RESTful Web Services framework.
 * 
 * @author Damjan Vučina
 */
@Path("/info")
public class ImageInfoJSON {

	/**
	 * Gets the details of a designated image. Image is defined by its name,
	 * description and tags.
	 *
	 * @param name
	 *            the name of the image
	 * @return the image details
	 */
	// NOTICE: DisplayImageInfoServlet serves the same purpose but does not offer
	// RESTful Web service support. This class is used by default.
	@Path("{arg}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImageInfo(@PathParam("arg") String name) {
		Map<String, Photo> photos = PhotoDB.getPhotos();
		Photo selectedPhoto = photos.get(name);

		JSONObject result = new JSONObject();
		result.put("name", selectedPhoto.getName());
		result.put("description", selectedPhoto.getDescription());
		result.put("tags", selectedPhoto.getTags());

		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
