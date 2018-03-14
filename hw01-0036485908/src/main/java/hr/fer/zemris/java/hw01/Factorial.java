package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The Class Factorial.
 *
 * @author Damjan Vučina
 * @version 1.0
 * 
 */
public class Factorial {

	/**
	 * The method invoked when the program is run.
	 *
	 * @param args
	 *            Command Line arguments. They are not used here.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");

			if (sc.hasNextInt()) {
				int input = sc.nextInt();

				if (input >= 1 && input <= 20) {
					System.out.println(input + "! = " + calculateFactorial(input));
				} else {
					System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
				}

			} else if (sc.hasNext("kraj")) {
				System.out.println("Doviđenja.");
				break;

			} else {
				System.out.println("'" + sc.next() + "' nije cijeli broj.");
			}
		}

		sc.close();

	}

	/**
	 * Method that calculates the factorial of the given number.
	 *
	 * @param input
	 *            Given argument
	 * @return calculated factorial number as long value
	 */
	public static long calculateFactorial(int input) {
		if (input < 0) {
			throw new IllegalArgumentException("Method argument must not be negative.");
		}
		if (input == 0 || input == 1) {
			return 1;
		} else {
			return input * calculateFactorial(input - 1);
		}

	}
}
