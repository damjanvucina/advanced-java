package hr.fer.zemris.java.custom.collections;

import java.awt.SecondaryLoop;

import org.omg.CORBA.PUBLIC_MEMBER;

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

}
