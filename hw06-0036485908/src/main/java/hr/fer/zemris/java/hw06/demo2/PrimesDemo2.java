package hr.fer.zemris.java.hw06.demo2;

/**
 * Class used for demonstrating the functionality of PrimesCollection class.
 * 
 * @author Damjan Vučina
 */
public class PrimesDemo2 {

	/**
	 * The main method. Invoked when the program is run.
	 *
	 * @param args
	 *            the arguments. Notice: not used here.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
