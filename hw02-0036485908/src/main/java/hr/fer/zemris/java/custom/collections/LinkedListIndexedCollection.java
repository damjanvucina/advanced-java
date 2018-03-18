package hr.fer.zemris.java.custom.collections;

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

	public LinkedListIndexedCollection(LinkedListIndexedCollection collection) {
		size = collection.size;
		first = collection.first;
		last = collection.last;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		insert(value, size);
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
		return current;
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

		} else if (index == size - 1) {// last and not the only one
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

		} else if (index == size - 1) {// last and not the only one
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

}
