package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * The class that represents a resizable array-backed collection of objects.
 * Storage of null references is not allowed. Duplicate elements are allowed.
 * Instance of this class is defined by its size (current number of elements
 * stored), capacity(maximum number of elements that could be stored) and array
 * which contains the elements.
 * 
 * @author Damjan Vučina
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * The Constant DEFAULT_CAPACITY that represents default array capacity if none
	 * was provided by the user.
	 */
	public static final int DEFAULT_CAPACITY = 16;

	/** The size of the array e.g. number of the currently stored elements. */
	private int size;

	/**
	 * The capacity of the array e.g. number of the elements that could be stored.
	 * Gets doubled every time the array grows full.
	 */
	private int capacity;

	/** The array that contains all the elements of the collection. */
	private Object[] elements;

	/**
	 * Instantiates a new array indexed collection, by creating the copy of the
	 * collection provided in the argument. If initial capacity is bigger than the
	 * number of the elements in the collection than that number becomes the
	 * capacity of the newly created collection.If initial capacity is smaller than
	 * the number of the elements in the collection than the collection's size is
	 * used as the capacity of the newly created one.
	 *
	 * @param collection
	 *            the collection whose elements are to be copied to the newly
	 *            created collection
	 * @param initialCapacity
	 *            the initial capacity of the newly created collection.If initial
	 *            capacity is bigger than the number of the elements in the
	 *            collection than that number becomes the capacity of the newly
	 *            created collection.If initial capacity is smaller than the number
	 *            of the elements in the collection than the collection's size is
	 *            used as the capacity of the newly created one.
	 * 
	 * @throws NullPointerException
	 *             if the collection provided as argument is null
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null) {
			throw new NullPointerException("Collection provided as argument cannot be null");
		} else if (initialCapacity < collection.size()) {

			capacity = collection.size();
			elements = new Object[capacity];

			addAll(collection);
		} else {
			capacity = initialCapacity;
			elements = new Object[capacity];

			addAll(collection);
		}
	}

	/**
	 * Instantiates a new array indexed collection and copies all of the elements
	 * from the collection provided as the argument to it.
	 *
	 * @param collection
	 *            the collection whose elements are to be copied to the newly
	 *            created collection
	 * @throws NullPointerException
	 *             if the collection provided as argument is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, collection.size());
	}

	/**
	 * Instantiates a new array indexed collection and sets its capacity to the
	 * value provided by the argument. Capacity has to be positive.
	 *
	 * @param initialCapacity
	 *            the initial capacity of the array indexed collection
	 * @throws IllegalArgumentException
	 *             if the user tries to set the capacity to the non-positive value
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
					"Initial capacity of the array cannot be less than 1, was: " + initialCapacity);
		} else {
			capacity = initialCapacity;
			elements = new Object[capacity];
		}
	}

	/**
	 * Instantiates a new array indexed collection and sets its capacity to the
	 * default value of 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Counts the number of elements stored in the collection.
	 * 
	 * @return number of elements stored in the collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the object provided as the argument to the collection. .
	 *
	 * @param value
	 *            object which is to be added to the collection
	 * @throws NullPointerException
	 *             if the user tries to add null value to the collection
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Method that is invoked when a collection grows full and no elements can be
	 * stored anymore. It creates a new array, double in size, and copies all of the
	 * elements of the old array to it.
	 */
	public void doubleCapacity() {
		capacity *= 2;
		elements = Arrays.copyOf(elements, capacity);
	}

	/**
	 * Checks whether the collection contains the object provided as the argument.
	 * It is OK to ask if the collection contains null value.
	 * 
	 * @param value
	 *            Element whose presence in this collection is to be tested
	 * @return true, if the collection contains the object provided as the argument,
	 *         as determined by the equals method, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes a single instance of the element provided as the argument from the
	 * collection if it is present within the collection.
	 * 
	 * @param value
	 *            element to be removed from the collection if it is present within
	 *            the collection
	 * @return true, if the element was present and successfully removed from the
	 *         collection
	 */
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

	/**
	 * Method that is used for retrieving all of the elements from the collection as
	 * an array. The method returns the elements in the same order they were
	 * inserted in to this collection.
	 *
	 * @return the array containing all of the elements of this collection
	 * 
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * Performs the action specified by the argument on all of the elements of this
	 * collection. Required action is performed in order of the iteration of the
	 * elements.
	 * 
	 *
	 * @param processor
	 *            Instance of a class whose methods describes the action that is
	 *            about to be performed on all of the elements of this collection.
	 */
	@Override
	public void forEach(Processor processor) {
		processor = Objects.requireNonNull(processor, "Processor argument cannot be null");

		for (Object object : elements) {
			if (object != null) {
				processor.process(object);
			}
		}
	}

	/**
	 * Clears the collection by removing all elements from it. The collection will
	 * be empty after this method returns.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Gets the object that is stored at the position provided in the argument.
	 * Valid indexes are 0 to size-1.
	 *
	 * @param index
	 *            the index of the collection from which the element is to be returned
	 * @return the object that is about to be returned
	 * @throws IndexOutOfBoundsException
	 *             if the index provided in the argument is invalid
	 */
	public Object get(int index) {
		validateIndex(index, size - 1);
		return elements[index];

	}

	/**
	 * Method that is invoked to perform the validation of the index of the
	 * collection before performing operations such as getting and inserting
	 * elements to the collection.
	 *
	 * @param index
	 *            the index of the collection
	 * @param limit
	 *            the maximum legal value of the index
	 */
	public void validateIndex(int index, int limit) {
		if (index < 0 || index > limit) {
			throw new IndexOutOfBoundsException("Index is out of range.");
		}
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * If the element is inserted somewhere in the middle of the array, all elements
	 * at the position provided in the argument and on the right of it are shifted
	 * one position to the right. The legal positions are 0 to size.
	 *
	 * @param value
	 *            the object that is about to be inserted to the collection
	 * @param position
	 *            the position at which the object is to be inserted
	 * @throws IndexOutOfBoundsException
	 *             if position of the insertion is not between 0 and size of the
	 *             collection
	 * @throws NullPointerException
	 *             if the user tries to insert null value to the collection
	 */
	public void insert(Object value, int position) {		
		int temporary = size;

		value = Objects.requireNonNull(value, "Null cannot be inserted into the collection");
		validateIndex(position, size);
		if (size == capacity) {
			doubleCapacity();
		}

		while (temporary > position) {
			elements[temporary] = elements[temporary - 1];
			temporary--;
		}

		elements[position] = value;
		size++;
	}

	/**
	 * Searches the collection and gets the index of the first occurence of the
	 * element provided in the argument within the collection. If the element is not
	 * present, method returns -1. It is OK to ask if the collection contains null
	 * element.
	 *
	 * @param value
	 *            the object whose presence within the collection is to be tested
	 * @return the index of the object in the collection or -1 if the element is not
	 *         present
	 */
	public int indexOf(Object value) {
		if(value==null) {
			return -1; //optimization
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes the object at the specified index from this collection. Element that
	 * was previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 *
	 * @param index
	 *            the index of the element that is to be removed from the collection
	 * @throws IndexOutOfBoundsException
	 *             if the index is less than 0 or bigger than collection's size
	 */
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
