package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The class that is used for factorial number calculation. User enters numbers
 * ranging from 1 to 20. Program ends when the user enters "kraj" word.
 *
 * @author Damjan Vučina
 * @version 1.0
 * 
 */
public class Factorial {

	public static final int MINIMUM_INPUT = 0;
	public static final int MAXIMUM_INPUT = 20;

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
			String line = sc.nextLine();

			try {
				int input = Integer.parseInt(line);
				

				if (input >= MINIMUM_INPUT && input <= MAXIMUM_INPUT) {
					System.out.println(input + "! = " + calculateFactorial(input));
				} else {
					System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
				}
				
			} catch (NumberFormatException ex) {
				if (line.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.println("'" + line + "' nije cijeli broj.");
			}
		}
		sc.close();
	}

	/**
	 * Method that calculates the factorial of the given number. Complexity: O(n)
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
