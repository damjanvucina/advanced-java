package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The Class that is used for storing objects that map keys to values. It
 * contains a private "Entry" class which is used for storing elements to this
 * dictionary. It cannot contain duplicate nor null keys but it can store both
 * duplicate and null values. Dicitionary class provides user with standard
 * methods for managing maps such as size, clear, get and put. Capacity of the
 * array is automatically doubled whenever the array grows full.
 * 
 * @author Damjan Vučina
 */
public class Dictionary {

	/** The elements of this dictionary. */
	private ArrayIndexedCollection elements;

	/**
	 * Instantiates a new dictionary with default capacity set to 16. When the array
	 * grows full its capacity is doubled. grows full, its capacity is doubled.
	 */
	public Dictionary() {
		elements = new ArrayIndexedCollection();
	}

	/**
	 * Class that represents a single item of this dictionary and maps its key and
	 * value. It cannot contain duplicate nor null keys but it can store both
	 * duplicate and null values. Two entries are considered equal if they have
	 * equal keys.
	 */
	private class Entry {

		/** The key of the element of this dictionary. */
		Object key;

		/** The value of the element of this dictionary. */
		Object value;

		/**
		 * Instantiates a new element of this dictionary end sets its key and value
		 * properties.
		 *
		 * @param key
		 *            the key of the element of this dictionary.
		 * @param value
		 *            the value of the element of this dictionary.
		 */
		public Entry(Object key, Object value) {
			key = Objects.requireNonNull(key, "Key of the entry cannot be set to null.");

			this.key = key;
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return String.valueOf(key) + " " + String.valueOf(value);
		}

		/**
		 * Gets the outer type of this inner Entry class This is a helper method used by
		 * equals() and hashcode() methods.
		 *
		 * @return the outer type of this entry
		 */
		private Dictionary getOuterType() {
			return Dictionary.this;
		}
	}

	/**
	 * Checks if the dictionary is empty, i.e. contains no entries.
	 *
	 * @return true, if the dictionary is empty
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Calculates how many elements are currently stored in this dictionary.
	 *
	 * @return the number of elements that are currently stored in this dictionary.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Clears all elements of this dictionary.
	 */
	public void clear() {
		elements.clear();
	}

	/**
	 * Instantiates an Entry class with given key and value arguments after
	 * validating that given key is not null and stores the created entry in this
	 * dictionary. If the dictionary already contains an elements with the same key,
	 * the newly created entry overwrites the old one as this dictionary cannot
	 * store duplicate elements, i.e. elements with equal key properties.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(Object key, Object value) {
		Entry entry = new Entry(key, value);
		int index = elements.indexOf(entry);

		if (index != -1) {
			elements.remove(index);
		}

		elements.add(entry);
	}

	/**
	 * Gets the element of this dictionary with the key property equal to the one
	 * specified in method argument. Method returns that entry if the element is
	 * found or null otherwise.
	 *
	 * @param key
	 *            the key of the element that is requested by user
	 * @return the object that is found after searching elements of this dictionary
	 */
	public Object get(Object key) {
		int index = elements.indexOf(new Entry(key, null));

		return (index != -1) ? ((Entry) elements.get(index)).value : null;
	}
}
