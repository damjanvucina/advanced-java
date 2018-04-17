package hr.fer.zemris.java.hw06.demo2;

/**
 * Class used for demonstrating the functionality of PrimesCollection class.
 * 
 * @author Damjan Vučina
 */
public class PrimesDemo1 {

	/**
	 * The main method. Invoked when the program is run.
	 *
	 * @param args
	 *            the arguments. Notice: not used here.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
