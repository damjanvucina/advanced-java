package hr.fer.zemris.java.hw06.observer2;

/**
 * Demo class which presents the expected integration and behavior of classes in
 * this Observer pattern. "Observer pattern" is a software design pattern in
 * which an object, called the Subject, maintains a list of its dependents,
 * called Observers, and notifies them automatically of any state changes,
 * usually by calling one of their methods, method valueChanged in this case.
 * 
 * @author Damjan Vučina
 */
public class ObserverExample {

	/**
	 * The main method invoked when the user starts the program.
	 *
	 * @param args
	 *            the arguments. Notice: not used here.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
