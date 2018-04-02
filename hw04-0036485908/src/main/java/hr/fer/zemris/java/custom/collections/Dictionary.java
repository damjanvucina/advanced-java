package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

public class Dictionary {

	private ArrayIndexedCollection elements;

	public Dictionary() {
		elements = new ArrayIndexedCollection();
	}

	private class Entry {
		Object key;
		Object value;

		public Entry(Object key, Object value) {
			key = Objects.requireNonNull(key, "Key of the entry cannot be set to null.");

			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

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
		
		public String toString() {
			return String.valueOf(key) + " " + String.valueOf(value);
		}

		private Dictionary getOuterType() {
			return Dictionary.this;
		}
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public int size() {
		return elements.size();
	}

	public void clear() {
		elements.clear();
	}

	public void put(Object key, Object value) {
		Entry entry = new Entry(key, value);
		int index = elements.indexOf(entry);

		if (index != -1) {
			elements.remove(index);
		}

		elements.add(entry);
	}

	public Object get(Object key) {
		int index = elements.indexOf(new Entry(key, null));

		return (index != -1) ? elements.get(index) : null;
	}
}
