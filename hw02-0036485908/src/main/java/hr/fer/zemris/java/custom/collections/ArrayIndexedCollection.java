package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

public class ArrayIndexedCollection extends Collection {

	public static final int DEFAULT_SIZE = 16;

	private int size;
	private int capacity;
	private Object[] elements;

	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null) {
			throw new NullPointerException("Collection provided as argument cannot be null");
		} else if (initialCapacity < collection.size()) {
			addAll(collection);
		} else {
			// dopuni ovo
		}
	}

	public ArrayIndexedCollection(Collection collection) {
		this(collection, collection.size());
	}

	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Initial capacity of the array cannot be less than 1, was: " + initialCapacity);
		} else {
			capacity = initialCapacity;
			elements = new Object[capacity];
		}
	}

	public ArrayIndexedCollection() {
		this(DEFAULT_SIZE);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		insert(value, size);
	}

	public void doubleCapacity() {
		capacity *= 2;
		elements = Arrays.copyOf(elements, capacity);
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);

		if (index == -1) {
			return false;
		} else {
			remove(index);
			return true;
		}
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	@Override
	public void forEach(Processor processor) {
		processor = Objects.requireNonNull(processor, "Processor argument cannot be null");

		for (Object object : elements) {
			processor.process(object);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	public Object get(int index) {
		validateIndex(index, size - 1);
		return elements[index];

	}

	public void validateIndex(int index, int limit) {
		if (index < 0 || index > limit) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + limit + ", was: " + index);
		}
	}

	public void insert(Object value, int position) {
		int index = position - 1;
		int temporary = size;

		value = Objects.requireNonNull(value, "Null cannot be inserted into the collection");
		validateIndex(index, size);
		if (size == capacity) {
			doubleCapacity();
		}

		while (temporary > index) {
			elements[temporary] = elements[temporary - 1];
			temporary--;
		}

		elements[index] = value;
		size++;
	}

	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public void remove(int index) {
		validateIndex(index, size - 1);

		while (index < size - 1) {
			elements[index] = elements[index + 1];
			index++;
		}

		elements[index] = null;
		size--;
	}
}
