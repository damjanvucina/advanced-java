package hr.fer.zemris.java.hw17.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;

import hr.fer.zemris.java.models.Photo;

/**
 * Class that is used for acquiring all the tags from the dummy database txt
 * file. This class is built upon Jersey RESTful Web Services framework.
 * 
 * @author Damjan Vučina
 */
@Path("/tags")
public class TagsJSON {

	/** The Constant RESOURCE_FILE. */
	public static final String RESOURCE_FILE = "/WEB-INF/opisnik.txt";

	/**
	 * Gets all the tags. from the dummy database txt file.
	 *
	 * @return the tags
	 */
	// NOTICE:GetTagsServlet serves the same purpose but does not offer
	// RESTful Web service support. This class is used by default.
	@GET
	@Produces("application/json")
	public Response getTags() {
		Map<String, Photo> photos = PhotoDB.getPhotos();
		Set<String> tags = new HashSet<>();
		photos.forEach((key, value) -> tags.addAll(value.getTags()));

		JSONArray jTags = new JSONArray();
		for (String tag : tags) {
			jTags.put(tag);
		}
		return Response.status(Status.OK).entity(jTags.toString()).build();
	}
}
