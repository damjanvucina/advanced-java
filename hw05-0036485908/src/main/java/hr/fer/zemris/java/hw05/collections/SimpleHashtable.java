package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.abs;
import static java.lang.Math.log10;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	public static final double LOAD_FACTOR = 0.75;
	public static final int STARTING_SLOT = -1;
	public static final int DEFAULT_CAPACITY = 16;
	public static final double LOG10_2 = log10(2);

	private TableEntry<K, V>[] table;
	private int size;
	private int modificationCount;

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
		table = new TableEntry[capacity];
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
			table[index] = new TableEntry<K, V>(key, value);
			size++;
			modificationCount++;

		} else if (current.getKey().equals(key)) {
			current.setValue(value);

		} else {
			TableEntry<K, V> entry = new TableEntry<K, V>(key, value);
			current.next = entry;
			size++;
			modificationCount++;
		}

		verifyCapacity();
	}

	@SuppressWarnings("unchecked")
	private void verifyCapacity() {
		if (size >= table.length * LOAD_FACTOR) {

			int previousCapacity = table.length;
			TableEntry<K, V>[] copy = new TableEntry[previousCapacity];
			copy = Arrays.copyOf(table, previousCapacity);

			clear();
			doubleCapacity(previousCapacity);
			refillTable(copy);
		}
	}

	private void refillTable(TableEntry<K, V>[] copy) {
		for (int i = 0, length = copy.length; i < length; i++) {
			TableEntry<K, V> current = copy[i];

			while (current != null) {
				put(current.key, current.value);
				current = current.next;
			}
		}
	}

	public void clear() {
		for (int i = 0, length = table.length; i < length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	@SuppressWarnings("unchecked")
	private void doubleCapacity(int previousCapacity) {
		table = new TableEntry[previousCapacity * 2];
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

	@SuppressWarnings("unchecked")
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> current : table) {
			while (current != null) {

				if (current.getValue() == (V) value || (current != null && current.getValue().equals((V) value))) {
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

		if (current != null && current.key.equals(key)) {
			if (previous != null) {
				previous.next = current.next;
				// current.next = null;

			} else {
				table[index] = null;
			}

			size--;
			modificationCount++;
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

	public int getTableLength() {
		return table.length;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private int processedElements = 0;
		private int currentSlot = STARTING_SLOT;
		private TableEntry<K, V> current;
		private int modificationCountCopy=modificationCount;

		@Override
		public boolean hasNext() {
			if (modificationCountCopy != modificationCount) {
				concurrentModificationOccured();
			}
			return processedElements < size();
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements are available in this Hashtable.");
			}

			if (current == null || (current != null && current.next == null)) {
				while (current == null || (current != null && current.next == null)) {
					current = table[++currentSlot];
				}

			} else {
				current = current.next;
			}

			processedElements++;
			return current;
		}

		@Override
		public void remove() {
			if (modificationCountCopy != modificationCount) {
				concurrentModificationOccured();
			}

			if (current == null) {// dodaj bacanje exceptiona za uzastopne pozive removea
				throw new IllegalStateException(
						"The remove() method can only be invoked after next() method,  only once per call to next().");
			}

			SimpleHashtable.this.remove(current.getKey());
			
			modificationCount++;
			modificationCountCopy++;
		}

		private void concurrentModificationOccured() {
			throw new ConcurrentModificationException(
					"Hashtable has been updated by an object other than this iterator.");
		}

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
