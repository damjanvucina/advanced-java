package hr.fer.zemris.java.hw17.rest;

import java.util.Map;

import hr.fer.zemris.java.models.Photo;

/**
 * The class that serves as a storage for the dummy photo collection. Photo is
 * defined by its name, description and tags. The collection is to be initalized
 * by the servlet listener class.
 * 
 * @author Damjan Vučina
 */
public class PhotoDB {

	/** The photos. Initalized by the servlet listener. */
	private static Map<String, Photo> photos;

	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public static Map<String, Photo> getPhotos() {
		return photos;
	}

	/**
	 * Sets the photos.
	 *
	 * @param listenerPhotos
	 *            the listener photos
	 */
	public static void setPhotos(Map<String, Photo> listenerPhotos) {
		photos = listenerPhotos;
	}

}
