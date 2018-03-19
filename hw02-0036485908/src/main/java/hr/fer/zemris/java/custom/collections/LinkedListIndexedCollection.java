package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;

	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}

	public LinkedListIndexedCollection() {
	}

	public LinkedListIndexedCollection(Collection collection) {
		// size=collection.size();
		addAll(collection);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		insert(value, size + 1);
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

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

	public void validateIndex(int index, int limit) {
		if (index < 0 || index > limit) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + limit + ", was: " + index);
		}
	}

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

	public int indexOf(Object value) {
		int index = 0;
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

	@Override
	public Object[] toArray() {
		if (size == 0) {
			return null;
		}
		Object[] array = new Object[size];
		int i = 0;
		for (ListNode current = first; current != null; current = current.next) {
			array[i++] = current.value;
		}
		return array;
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
	public void forEach(Processor processor) {
		processor = Objects.requireNonNull(processor, "Processor argument cannot be null");

		for (Object object : toArray()) {
			processor.process(object);
		}
	}

	public static void main(String[] args) {

		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		// This is local class representing a Processor which writes objects to
		// System.out
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;
		System.out.println("col elements:");
		col.forEach(new P());
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(new Integer(20)); // removes 20 from collection (at position 0).
	}

}
