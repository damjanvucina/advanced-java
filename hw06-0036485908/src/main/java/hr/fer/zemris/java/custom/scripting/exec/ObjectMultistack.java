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

		if (!(name instanceof String)) {
			throw new ObjectMultistackException(
					"Name attribute must be an instance of class String, was: " + name.getClass().getSimpleName());
		}

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
			value = Objects.requireNonNull(value, "Value of MultistackEntry cannot be set to null.");

			this.value = value;
		}
	}
}
