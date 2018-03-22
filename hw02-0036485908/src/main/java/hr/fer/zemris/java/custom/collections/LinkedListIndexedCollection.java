package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The Class that represents a linked list-backed collection of objects. This
 * class defines private static class ListNode with pointers to previous and
 * next list node that represent elements of the list. Duplicate elements are
 * allowed. Storage of null value is not allowed.
 * 
 * @author Damjan Vučina
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/** The size of the list, e.g. number of the elements within the list */
	private int size;

	/** Pointer to the first element of the list. */
	private ListNode first;

	/** Pointer to the last element of the list. */
	private ListNode last;

	/**
	 * The class that represents a node within the list, e.g. an element of the
	 * list.
	 */
	private static class ListNode {

		/** The reference to the previous node. */
		ListNode previous;

		/** The reference to the next node. */
		ListNode next;

		/** The value of the node. */
		Object value;
	}

	/**
	 * Instantiates a new linked list indexed collection.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Instantiates a new linked list indexed collection by creating the exact same
	 * copy to the collection the user provided in the arguments.
	 *
	 * @param collection
	 *            the collection that is to be copied
	 */
	public LinkedListIndexedCollection(Collection collection) {
		addAll(collection);
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
		insert(value, size + 1);
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
	 * Clears the collection by removing all elements from it. The collection will
	 * be empty after this method returns.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Gets the object that is stored at the position provided in the argument.
	 * Valid indexes are 0 to size-1.
	 *
	 * @param index
	 *            the index of the collection from which the element is to be
	 *            returned
	 * @return the object that is about to be returned
	 * @throws IndexOutOfBoundsException
	 *             if the index provided in the argument is invalid
	 */
	public Object get(int index) {
		validateIndex(index, size - 1);
		ListNode current;

		if (index < size / 2) {
			current = first;
			while (index-- > 0) {
				current = current.next;
			}
		} else {
			current = last;
			while (++index < size) {
				current = current.previous;
			}
		}
		return current.value;
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
			throw new IndexOutOfBoundsException("Index must be between 0 and " + limit + ", was: " + index);
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
		value = Objects.requireNonNull(value, "You cannot add null elements to the collection");
		int index = position - 1;
		validateIndex(index, size);

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (index == 0) {
			if (size > 0) { // first and not the only one
				first.previous = newNode;
				newNode.next = first;
				first = newNode;

			} else { // first and the only one
				first = newNode;
				last = newNode;
			}

		} else if (index == size) {// last and not the only one
			last.next = newNode;
			newNode.previous = last;
			last = newNode;

		} else { // somewhere in the middle
			ListNode latter = first;

			while (index-- > 0) {
				latter = latter.next; // new node is placed before the latter one
			}

			latter.previous.next = newNode;
			newNode.previous = latter.previous;
			latter.previous = newNode;
			newNode.next = latter;
		}
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
		int index = 0;
		if (value == null) {
			return -1; // optimization
		}
		if (first == null) {
			return -1;
		}
		for (ListNode current = first; current != null; current = current.next, index++) {
			if (current.value.equals(value)) {
				return index;
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

		if (index == 0) {
			if (size > 1) { // first and not the only one
				first.next.previous = null;
				first = first.next;
			} else { // first and the only one
				first = null;
				last = null;
			}

		} else if (index == size) {// last and not the only one
			last.previous.next = null;
			last = last.previous;

		} else { // somewhere in the middle
			ListNode latter = first;

			while (index-- > 0) {
				latter = latter.next;
			}
			latter.previous.next = latter.next;
			latter.next.previous = latter.previous;
		}
		size--;
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
		Object[] array = new Object[size];
		int i = 0;
		for (ListNode current = first; current != null; current = current.next) {
			array[i++] = current.value;
		}
		return array;
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

		for (Object object : toArray()) {
			processor.process(object);
		}
	}

}
