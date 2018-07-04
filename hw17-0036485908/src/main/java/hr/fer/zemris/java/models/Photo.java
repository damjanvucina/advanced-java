package hr.fer.zemris.java.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Photo {
	private static final String TAG_SPLITTER = ", ";

	private String name;
	private String description;
	private List<String> tags;

	public Photo(String name, String description, String nonFormattedTags) {
		this.name = name;
		this.description = description;

		tags = new ArrayList<>();
		tags.addAll(formatTags(nonFormattedTags));
	}

	private List<String> formatTags(String nonFormattedTags) {
		return Arrays.asList(nonFormattedTags.split(TAG_SPLITTER));
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
