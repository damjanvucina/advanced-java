package hr.fer.zemris.java.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class that models a single image defined by its name, description and
 * tags.
 * 
 * @author Damjan Vučina
 */
public class Photo {

	/** The Constant TAG_SPLITTER. */
	private static final String TAG_SPLITTER = ", ";

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The tags. */
	private List<String> tags;

	/**
	 * Instantiates a new photo.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param nonFormattedTags
	 *            the non formatted tags
	 */
	public Photo(String name, String description, String nonFormattedTags) {
		this.name = name;
		this.description = description;

		tags = new ArrayList<>();
		tags.addAll(formatTags(nonFormattedTags));
	}

	/**
	 * Format tags.
	 *
	 * @param nonFormattedTags
	 *            the non formatted tags
	 * @return the list
	 */
	private List<String> formatTags(String nonFormattedTags) {
		return Arrays.asList(nonFormattedTags.split(TAG_SPLITTER));
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the tags.
	 *
	 * @param tags
	 *            the new tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
