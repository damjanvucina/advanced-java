package hr.fer.zemris.java.models;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Photo {
	private static final String TAG_SPLITTER = ", ";

	private Path path;
	private String description;
	private List<String> tags;

	public Photo(String path, String description, String nonFormattedTags) {
		this.path = Paths.get(path);
		this.description = description;

		tags = new ArrayList<>();
		tags.addAll(formatTags(nonFormattedTags));
	}

	private List<String> formatTags(String nonFormattedTags) {
		return Arrays.asList(nonFormattedTags.split(TAG_SPLITTER));
	}

	public Path getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
