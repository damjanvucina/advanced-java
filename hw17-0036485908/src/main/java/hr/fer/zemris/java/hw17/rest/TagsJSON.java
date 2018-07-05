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

@Path("/tags")
public class TagsJSON {
	public static final String RESOURCE_FILE = "/WEB-INF/opisnik.txt";

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
