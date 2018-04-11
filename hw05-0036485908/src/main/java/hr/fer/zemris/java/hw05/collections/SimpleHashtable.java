package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.abs;
import static java.lang.Math.log10;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The class that represents a simple hash table that maps keys to values. Any
 * non-null object can be used as a key, while any object whatsoever can be used
 * as value (even null). This class provides user with two public constructors:
 * the default one that creates a 16 slot table and another one in case user
 * wants to specify number of slots himself. However, specified capacity can
 * only be a power of two. If this is not true, the capacity will be normalized
 * to the first power of number two bigger than the entered initial capacity. If
 * initial capacity is less than one, an IllegalArgumentException is thrown.
 * This hashtable uses overlay policy and as such provides user with the
 * possiblity of storing more elements than the number of slots. This
 * implementation provides user with standard methods for working with
 * hashtables, such as get, put, clear, size, containsKey, containsValue, etc.
 * Since the efficiency of any hashtable deteriorates rapidly once the number of
 * stored elements reaches approximately 75% of number of slots, a doubling
 * capacity policy is in use. Load factor of 0.75 is used since it makes for a
 * good tradeoff between time and space costs.
 *
 * @param <K>
 *            the type of the key
 * @param <V>
 *            the type of the value
 * 
 * @author Damjan Vučina
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/** The Constant LOAD_FACTOR. */
	public static final double LOAD_FACTOR = 0.75;

	/** The Constant STARTING_SLOT. */
	public static final int STARTING_SLOT = -1;

	/** The Constant DEFAULT_CAPACITY. */
	public static final int DEFAULT_CAPACITY = 16;

	/** The Constant LOG10_2. */
	public static final double LOG10_2 = log10(2);

	/** The elements of this simple hashtable. */
	private TableEntry<K, V>[] table;

	/** The size of this simple hashtable.. */
	private int size;

	/** The number of modification performed on this simple hashtable. */
	private int modificationCount;

	/**
	 * Instantiates a new simple hashtable with default capacity of 16 slots.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Instantiates a new simple hashtable with user specified capacity. Specified
	 * capacity can only be a power of two. If this is not true, the capacity will
	 * be normalized to the first power of number two bigger than the entered
	 * initial capacity. If initial capacity is less than one, an
	 * IllegalArgumentException is thrown.
	 *
	 * @param capacity
	 *            number of slots of this hashtable
	 * @throws IllegalArgumentException
	 *             if capacity of the hashtable is to be set to a value less than
	 *             one.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"Capacity of the Hashtable cannot be set to a value less then 1, was: " + capacity);
		}

		capacity = normalizeTableCapacity(capacity);
		table = new TableEntry[capacity];
	}

	/**
	 * Normalize table capacity. Specified capacity can only be a power of two. If
	 * this is not true, the capacity will be normalized to the first power of
	 * number two bigger than the entered initial capacity.
	 *
	 * @param capacity
	 *            number of slots of this hashtable
	 * @return the normalized number of slots of this hashtable
	 */
	private int normalizeTableCapacity(int capacity) {
		while (log2(capacity) != (int) log2(capacity)) {
			capacity++;
		}

		return capacity;
	}

	/**
	 * Specified capacity can only be a power of two. If this is not true, the
	 * capacity will be normalized to the first power of number two bigger than the
	 * entered initial capacity. This method is in charge of this mathematical
	 * operation.
	 *
	 * @param capacity
	 *            number of slots of this hashtable
	 * @return the base two logarithm of the capacity
	 */
	private double log2(int capacity) {
		return log10(capacity) / LOG10_2;
	}

	/**
	 * Gets the size of this hashtable, e.g. the number of stored elements.
	 *
	 * @return the size of this hashtable, e.g. the number of stored elements
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Puts a new element to this hashtable. This hashtable stores instances of
	 * parameterized TableEntry class. Any non-null object can be used as a key,
	 * while any object whatsoever can be used as value (even null).
	 *
	 * @param key
	 *            the key of the TableEntry class
	 * @param value
	 *            the value of the TableEntry class
	 * 
	 * @throws NullPointerException
	 *             if the key of the TableEntry class is to be set to null.
	 */
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

	/**
	 * Verifies that the number of stored elements in this hashtable is less than
	 * 75% of the number of slots. Since the efficiency of any hashtable
	 * deteriorates rapidly once the number of stored elements reaches approximately
	 * 75% of number of slots, a doubling capacity policy is in use. Load factor of
	 * 0.75 is used since it makes for a good tradeoff between time and space costs.
	 * If this is not true this method doubles the capacity of this hashtable and
	 * returns its elements back to its place. NOTICE: since the capacity of the
	 * hashtable is used by hashcode function, elements could after doubling the
	 * hashtable's capacity end up in a different slot than the one they have been
	 * in prior to the doubling of the capacity.
	 */
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

	/**
	 * Refills the hashtable with doubled capacity with its elements. Since the
	 * efficiency of any hashtable deteriorates rapidly once the number of stored
	 * elements reaches approximately 75% of number of slots, a doubling capacity
	 * policy is in use. Load factor of 0.75 is used since it makes for a good
	 * tradeoff between time and space costs. If this is not true this method
	 * doubles the capacity of this hashtable and returns its elements back to its
	 * place. NOTICE: since the capacity of the hashtable is used by hashcode
	 * function, elements could after doubling the hashtable's capacity end up in a
	 * different slot than the one they have been in prior to the doubling of the
	 * capacity.
	 *
	 * @param copy
	 *            the copy
	 */
	private void refillTable(TableEntry<K, V>[] copy) {
		for (int i = 0, length = copy.length; i < length; i++) {
			TableEntry<K, V> current = copy[i];

			while (current != null) {
				put(current.key, current.value);
				current = current.next;
			}
		}
	}

	/**
	 * Clears this hashtable of all stored elements, i.e. insances of TableEntry
	 * class.
	 */
	public void clear() {
		for (int i = 0, length = table.length; i < length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Doubles the capacity of this hashtable.
	 *
	 * @param previousCapacity
	 *            the previous capacity
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity(int previousCapacity) {
		table = new TableEntry[previousCapacity * 2];
	}

	/**
	 * Calculates the index of the slot that the object with provided key is to be
	 * stored in.
	 *
	 * @param key
	 *            the key of the slot that the object to be stored in this hashtable
	 * @return the number of the slot that the object to be stored in this hashtable
	 */
	private int calculateIndex(Object key) {
		return abs(key.hashCode()) % table.length;
	}

	/**
	 * Extracts an instance of TableEntry class specified by its key property from
	 * this hashtable. Method via arguments also gets an instance of TableEntry
	 * class which represents the first element of the slot that the searched for
	 * TableEntry is a part of.
	 *
	 * @param current
	 *            first element of the slot that the searched for TableEntry is a
	 *            part of.
	 * @param key
	 *            the key of the earched for TableEntry
	 * @return the table entry
	 */
	private TableEntry<K, V> extractFromSlot(TableEntry<K, V> current, K key) {
		while (current != null && !current.key.equals(key) && current.next != null) {
			current = current.next;
		}

		return current;
	}

	/**
	 * Gets the TableEntry class specified by its key property from this hashtable.
	 *
	 * @param key
	 *            the key of the searched for TableEntry class
	 * @return the v value of the searched for TableEntry class
	 */
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

	/**
	 * Gets the size of this hashtable, i.e. the number of the currently stored
	 * TableEntry classes.
	 *
	 * @return the the number of the currently stored TableEntry classes.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if this hashtable contains an instance of TableEntry class with the
	 * same key as the one specified via argument
	 *
	 * @param key
	 *            the key of the searched for TableEntry class
	 * @return true, if successful
	 */
	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	/**
	 * Checks if this hashtable contains an instance of TableEntry class with the
	 * same value as the one specified via argument.
	 *
	 * @param value
	 *            the value of the searched for TableEntry class
	 * @return true, if successful
	 */
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

	/**
	 * Removes the TableEntry class identifed by its key from this hashtable.
	 *
	 * @param key
	 *            the key of the TableEntry class that is to be removed from this
	 *            hashtable
	 */
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
			if (previous == null) {// from the beginning
				table[index] = current.next;
			} else if (current.next == null) {// from the end
				previous.next = null;
			} else { // from the middle
				previous.next = current.next;
			}

			size--;
			modificationCount++;
		}
	}

	/**
	 * Checks if this hashtable is empty, i.e. contains zero TableEntry classes.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Prints a String representation of this hashtable.
	 */
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

	/**
	 * Gets the number of slots of this hashtable.
	 *
	 * @return the number of slots of this hashtable.
	 */
	public int getTableLength() {
		return table.length;
	}

	/**
	 * Returns a new instance of the class used for iterating over elements of this
	 * hashtable.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * The class that is used for iterating over the elements of this hashtable. It
	 * provides user with methods for checking if there are more non-processed
	 * elements, and for getting and removing of them.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/** The number of processed elements. */
		private int processedElements = 0;

		/** The current slot. */
		private int currentSlot = STARTING_SLOT;

		/** The number of the slots of this hashtable. */
		private int tableSize = size;

		/** The last processed element of this hashtable. */
		private TableEntry<K, V> current;

		/**
		 * The copy of the number that tracks modifications performed on this hashtable.
		 * Used for verifying that this hashtable has not been altered by any object
		 * other that an instance of this class.
		 */
		private int modificationCountCopy = modificationCount;

		/**
		 * Checks if there are more non-processed elements in this hashtable.
		 */
		@Override
		public boolean hasNext() {
			if (modificationCountCopy != modificationCount) {
				concurrentModificationOccured();
			}
			return processedElements < tableSize;
		}

		/**
		 * Gets the next non-processed elements in this hashtable.
		 * 
		 * @throws NoSuchElementException
		 *             if no more elements are available in this Hashtable.
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements are available in this Hashtable.");
			}

			if (current != null) {
				if (current.next == null) {
					current = table[++currentSlot];

				} else {
					current = current.next;
				}

			} else {
				while (current == null) {
					current = table[++currentSlot];
				}
			}

			processedElements++;
			return current;
		}

		/**
		 * Removes the last processed element from this Hashtable
		 * 
		 * @throws IllegalStateException
		 *             if the remove() method was not invoked after next() method, or if
		 *             it was invoked more than once per call to next().
		 */
		@Override
		public void remove() {
			if (modificationCountCopy != modificationCount) {
				concurrentModificationOccured();
			}

			if (current == null || !containsKey(current.key)) {// dodaj bacanje exceptiona za uzastopne pozive removea
				throw new IllegalStateException(
						"The remove() method can only be invoked after next() method,  only once per call to next().");
			}

			SimpleHashtable.this.remove(current.getKey());

			modificationCountCopy++;
		}

		/**
		 * Signifies that this hashtable has been altered by some object other that an
		 * instance of this class and throws appropriate exception.
		 * 
		 * @throws ConcurrentModificationException
		 *             if hashtable has been updated by an object other than this
		 *             iterator.
		 */
		private void concurrentModificationOccured() {
			throw new ConcurrentModificationException(
					"Hashtable has been updated by an object other than this iterator.");
		}

	}

	/**
	 * The class that represents an element of this hashtable. Any non-null object
	 * can be used as a key, while any object whatsoever can be used as value (even
	 * null).
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	public static class TableEntry<K, V> {

		/** The key of this TableEntry. */
		private K key;

		/** The value of this TableEntry. */
		private V value;

		/** The next TableEntry class in this hashtable. */
		private TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry.
		 *
		 * @param key
		 *            the key of this TableEntry
		 * @param value
		 *            the value of this TableEntry
		 */
		public TableEntry(K key, V value) {
			key = Objects.requireNonNull(key, "Key of the TableEntry cannot be set to null.");

			this.key = key;
			this.value = value;
		}

		/**
		 * Gets the key of this TableEntry.
		 *
		 * @return the key of this TableEntry
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Gets the value of this TableEntry.
		 *
		 * @return the value of this TableEntry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value of this TableEntry.
		 *
		 * @param value
		 *            the new value of this TableEntry
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Checks if two instances of TableEntry class are equal by calculating their
		 * hash. Two instances of TableEntry class are considered equal if they have
		 * identical key attributes.
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		/**
		 * Checks if two instances of TableEntry class are equal. Two instances of
		 * TableEntry class are considered equal if they have identical key attributes.
		 */
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
