package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.log10;
import static java.lang.Math.abs;

import java.util.Objects;

public class SimpleHashtable<K, V> {
	public static final int DEFAULT_CAPACITY = 16;
	public static final double LOG10_2 = log10(2);

	private TableEntry<K, V>[] table;
	private int size;

	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"Size of the Hashtable cannot be set to a value less then 1, was: " + capacity);
		}

		capacity = normalizeTableCapacity(capacity);
		table = (TableEntry<K, V>[]) new Object[capacity];
	}

	private int normalizeTableCapacity(int capacity) {
		while (log2(capacity) != (int) log2(capacity)) {
			capacity++;
		}

		return capacity;
	}

	private double log2(int capacity) {
		return log10(capacity) / LOG10_2;
	}

	public int getSize() {
		return size;
	}

	public void put(K key, V value) {
		key = Objects.requireNonNull(key, "Key of the TableEntry canot be set to null.");

		int index = calculateIndex(key);
		TableEntry<K, V> current = extractFromSlot(table[index], key);

		if (current == null) {
			current = new TableEntry<K, V>(key, value);
		} else if (current.getKey().equals(key)) {
			current.setValue(value);
		} else {
			current.next = new TableEntry<K, V>(key, value);
		}

		size++;
	}

	private int calculateIndex(Object key) {
		return abs(key.hashCode()) % table.length;
	}

	private TableEntry<K, V> extractFromSlot(TableEntry<K, V> current, K key) {
		while (current != null && !current.key.equals(key) && current.next != null) {
			current = current.next;
		}

		return current;
	}

	public V get(Object key) {
		if (key == null) {
			return null;// redundant but boosts performance
		}

		int index = calculateIndex(key);
		@SuppressWarnings("unchecked")
		TableEntry<K, V> current = extractFromSlot(table[index], (K) key);

		if (current != null && current.getKey().equals(key)) {
			return current.getValue();
		}

		return null;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		for (TableEntry<K, V> current : table) {
			while (current != null) {

				if (current.getValue().equals(value)) {
					return true;
				}
				current = current.next;
			}
		}
		return false;
	}

	public void remove(Object key) {
		if (key == null) {
			return;
		}

		TableEntry<K, V> previous = null;
		int index = calculateIndex(key);
		TableEntry<K, V> current = table[index];

		while (current != null && !current.key.equals(key)) {
			previous = current;
			current = current.next;
		}

		if (current != null && current.key.equals(key) && previous!=null) {
			previous.next=current.next;
			current.next=null;
			size--;
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
				entry = entry.next;
			}
		}

		sb.delete(sb.lastIndexOf(","), sb.length());
		sb.append("]");

		return sb.toString();
	}

	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value) {
			key = Objects.requireNonNull(key, "Key of the TableEntry cannot be set to null.");

			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

	}
}
