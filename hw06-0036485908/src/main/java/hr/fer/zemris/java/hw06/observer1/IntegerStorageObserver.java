package hr.fer.zemris.java.hw06.observer1;

/**
 * Interface that defines a method for communication with IntegerStorage class
 * that classes performing actions (Concrete observers) need to implement. This
 * interface is a part of "Observer pattern", software design pattern in which
 * an object, called the Subject, maintains a list of its dependents, called
 * Observers, and notifies them automatically of any state changes, usually by
 * calling one of their methods, method valueChanged in this case.
 * 
 * @author Damjan Vučina
 */
public interface IntegerStorageObserver {

	/**
	 * Method invoked by IntegerStorage class whenever a change to the stored value
	 * occurs. By invoking this method, IntegerStorage class notifies Concrete
	 * observers (classes that implement this interface) that a change to the value
	 * has occured so those classes can notify the end user appropriately.
	 * 
	 * @param istorage
	 *            the IntegerStorage class that stored the observed value
	 */
	public void valueChanged(IntegerStorage istorage);
}
