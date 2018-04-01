package hr.fer.zemris.java.custom.collections;

/**
 * The Class that represents a general purpose collection of objects. It
 * provides a single default constructor which creates an empty collection. It
 * allows user to do basic operations such as inserting, removing and iterating
 * over elements of the collection.
 * 
 * @author Damjan Vučina
 */
public class Collection {

	/**
	 * Default constructor that instantiates a new collection.
	 */
	protected Collection() {
	}

	/**
	 * Checks if the collection is empty.
	 *
	 * @return true if this collection contains no elements
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Counts the number of elements stored in the collection. Notice: The method is
	 * not implemented in this class.
	 * 
	 * @return number of elements stored in the collection.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the object provided as the argument to the collection. Notice: The
	 * method is not implemented in this class.
	 *
	 * @param value
	 *            object which is added to the collection
	 * 
	 */
	public void add(Object value) {
	}

	/**
	 * Checks whether the collection contains the object provided as the argument.
	 * It is OK to ask if the collection contains null value. Notice: The method is
	 * not implemented in this class.
	 * 
	 * @param value
	 *            Element whose presence in this collection is to be tested
	 * @return true, if the collection contains the object provided as the argument,
	 *         as determined by the equals method
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes a single instance of the element provided as the argument from the
	 * collection if it is present within the collection Notice: The method is not
	 * implemented in this class.
	 * 
	 * @param value
	 *            element to be removed from the collection if it is present within
	 *            the collection
	 * @return true, if the element was present and successfully removed from the
	 *         collection
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that is used for retrieving all of the elements from the collection as
	 * an array. The method returns the elements in the same order they were
	 * inserted in to this collection. Notice: The method is not implemented in this
	 * class.
	 *
	 * @return the array containing all of the elements of this collection
	 * @throws UnsupportedOperationException
	 *             always
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Exception thrown from class Collection");
	}

	/**
	 * Perform the action specified by the argument on all of the elements of this
	 * collection. Required action is performed in order of the iteration of the
	 * elements. Notice: The method is not implemented in this class.
	 * 
	 *
	 * @param processor
	 *            Instance of a class whose methods describes the action that is
	 *            about to be performed on all of the elements of this collection.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds all of the elements from the collection provided as the argument to this
	 * collection. The collection provided as the argument remains unchanged
	 *
	 * @param other
	 *            collection whose elements are about to be added to this collection
	 * 
	 */
	public void addAll(Collection other) {

		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new LocalProcessor());
	}

	/**
	 * Clears the collection by removing all elements from it. Notice: The method is
	 * not implemented in this class.
	 */
	public void clear() {
	}

}
