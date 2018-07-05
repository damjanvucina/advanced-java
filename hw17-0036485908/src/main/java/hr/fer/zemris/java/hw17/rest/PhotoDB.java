package hr.fer.zemris.java.hw17.rest;

import java.util.Map;

import hr.fer.zemris.java.models.Photo;

public class PhotoDB {
	private static Map<String, Photo> photos;

	public static Map<String, Photo> getPhotos() {
		return photos;
	}

	public static void setPhotos(Map<String, Photo> listenerPhotos) {
		photos = listenerPhotos;
	}

}
