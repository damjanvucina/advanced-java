package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectMultistack {

	private Map<String, MultistackEntry> map;

	public ObjectMultistack() {
		map = new HashMap<>();
	}

	public void push(String name, ValueWrapper valueWrapper) {
		name = Objects.requireNonNull(name,
				"Name attribute is a key in this ObjectMultistack and as such cannot be set to null.");

		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		MultistackEntry oldEntry = map.get(name);

		if (oldEntry != null) {
			newEntry.next = oldEntry;
		}

		map.put(name, newEntry);
	}

	public ValueWrapper pop(String name) {
		ValueWrapper poppedValue = peek(name);

		if (poppedValue != null) {
			map.put(name, map.get(name).next);
		}

		return poppedValue;
	}

	public ValueWrapper peek(String name) {
		name = Objects.requireNonNull(name, "This ObjectMultistack cannot store elements with key set to null.");

		MultistackEntry oldEntry = map.get(name);

		if (oldEntry == null) {
			throw new ObjectMultistackException("There are no elements to access.");
		}

		return oldEntry.value;
	}

	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}

	private static class MultistackEntry {

		ValueWrapper value;
		MultistackEntry next;

		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}

		public ValueWrapper getValue() {
			return value;
		}

		public MultistackEntry getNext() {
			return next;
		}

		public void setValue(ValueWrapper value) {
			this.value = value;
		}

		public void setNext(MultistackEntry next) {
			this.next = next;
		}
	}
}
