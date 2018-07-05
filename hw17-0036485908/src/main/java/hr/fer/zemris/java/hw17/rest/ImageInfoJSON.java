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

@Path("/info")
public class ImageInfoJSON {

	@Path("{arg}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuote(@PathParam("arg") String name) {
		Map<String, Photo> photos = PhotoDB.getPhotos();
		Photo selectedPhoto = photos.get(name);
		
		JSONObject result = new JSONObject();
		result.put("name", selectedPhoto.getName());
		result.put("description", selectedPhoto.getDescription());
		result.put("tags", selectedPhoto.getTags());
				
		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
