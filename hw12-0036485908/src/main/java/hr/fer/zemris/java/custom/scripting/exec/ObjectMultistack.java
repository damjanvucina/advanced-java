package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The class that represents a map like abstraction and maps keys to stacks. Key
 * must be an instance of String whilst value must be an instance of
 * ValueWrapper class. Notice: value cannot be null, but null can be
 * encapsulated in ValueWrapper class and stored as such. Values i.e. instances
 * of ValueWrapper class are prior to storing encapsulated in an instance of
 * MultistackEntry class. This policy enables ObjectMultistack class to provide
 * user with standard methods for working with stacks and perform operations
 * such as push, peek or pop in O(1) complexity.
 * 
 * @author Damjan Vučina
 */
public class ObjectMultistack {

	/** The map that maps Strings to stacks. */
	private Map<String, MultistackEntry> map;

	/**
	 * Instantiates a new ObjectMultistack.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Pushes given ValueWrappen to the stack identified by the String value key.
	 * Value cannot be null, but null can be encapsulated in ValueWrapper class and
	 * stored as such. Values i.e. instances of ValueWrapper class are prior to
	 * storing encapsulated in an instance of MultistackEntry class.
	 *
	 * @param name
	 *            the key of the stack
	 * @param valueWrapper
	 *            the ValueWrapper to be pushed to the stack identified by the name
	 *            attribute
	 * @throws NullPointerException
	 *             if the provided key of the stack is null
	 * @throws ObjectMultiStackException
	 *             if name attribute is not an instance of String
	 */
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

	/**
	 * Pops an instance of ValueWrapper class from the stack defined by the key
	 * name. Key must be an instance of String.
	 *
	 * @param name
	 *            the key of the stack
	 * @return the ValueWrapper to be popped from the stack identified by the name
	 *         attribute
	 * @throws NullPointerException
	 *             if the provided key of the stack is null
	 * @throws ObjectMultiStackException
	 *             if there are no elements to access.
	 */
	public ValueWrapper pop(String name) {
		ValueWrapper poppedValue = peek(name);

		if (poppedValue != null) {
			map.put(name, map.get(name).next);
		}

		return poppedValue;
	}

	/**
	 * Gets the top element from the stack identified by the key name. Key must be
	 * an instance of String.
	 *
	 * @param name
	 *            the key of the stack
	 * @return the instance ValueWrapper from the top of the stack identified by the
	 *         key name
	 * @throws NullPointerException
	 *             if the provided key of the stack is null
	 * @throws ObjectMultiStackException
	 *             if there are no elements to access.
	 * 
	 */
	public ValueWrapper peek(String name) {
		name = Objects.requireNonNull(name, "This ObjectMultistack cannot store elements with key set to null.");

		MultistackEntry oldEntry = map.get(name);

		if (oldEntry == null) {
			throw new ObjectMultistackException("There are no elements to access.");
		}

		return oldEntry.value;
	}

	/**
	 * Checks if the stack defined by the key name contains any elements, i.e.
	 * instances of ValueWrapper class.
	 *
	 * @param name
	 *            the key of the stack
	 * @return true, if the stack defined by the key name does not contain any
	 *         elements, i.e. instances of ValueWrapper class.
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}

	/**
	 * The Class MultistackEntry that all elements (instances of ValueWrapper class)
	 * to be stored to this map are encapsulated into. This policy enables
	 * ObjectMultistack class to provide user with standard methods for working with
	 * stacks and perform operations such as push, peek or pop in O(1) complexity.
	 * Notice: value cannot be null, but null can be encapsulated in ValueWrapper
	 * class and stored as such.
	 */
	private static class MultistackEntry {

		/** The value of the element of this map. */
		ValueWrapper value;

		/** The reference to the next element in a stack. */
		MultistackEntry next;

		/**
		 * Instantiates a new multistack entry. Notice: value cannot be null, but null
		 * can be encapsulated in ValueWrapper class and stored as such.
		 *
		 * @param value
		 *            The value of the element of this map.
		 * @throws NullPointerException
		 *             if value of MultistackEntry is to be set to null.
		 */
		public MultistackEntry(ValueWrapper value) {
			value = Objects.requireNonNull(value, "Value of MultistackEntry cannot be set to null.");

			this.value = value;
		}
	}
}
