package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class that represents a rectangle for the perimeter and surface calculation.
 * User can provide input via command line by entering two double numbers or via
 * console.
 *
 * @author Damjan Vučina
 * @version 1.0
 */
public class Rectangle {

	/**
	 * The method invoked when the program is run.
	 *
	 * @param args
	 *            Command Line arguments. You should run the program either with two
	 *            command line arguments or without them and then provide input via
	 *            console.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);

				if (width >= 0 && height >= 0) {
					printResult(width, height);
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
			} catch (NumberFormatException ex) {
				System.err.println("Argumenti se ne mogu parsirati.");
			} catch (NullPointerException ex) {
				System.err.println("Argumenti ne smiju biti null.");
			}

		} else if (args.length == 0) {
			Scanner sc = new Scanner(System.in);
			
			double width = getArguments(sc, "širinu");
			double height = getArguments(sc, "visinu");
			
			printResult(width, height);
			sc.close();
		} else {
			System.out.println(args.length + " nije dozvoljen broj argumenata.");
		}
	}

	/**
	 * Method which is used for demanding input from user in case no arguments were
	 * provided via command line.
	 *
	 * @param sc
	 *            Scanner which is used for reading user input.
	 * @param argument
	 *            Argument provided by the user
	 * @return Argument provided by the user which represents the length of
	 *         rectangle's side.
	 */
	public static double getArguments(Scanner sc, String argument) {
		while (true) {
			System.out.print("Unesite " + argument + " > ");

			if (sc.hasNextDouble()) {
				double input = sc.nextDouble();

				if (input >= 0) {
					return input;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}

			} else {
				System.out.println("'" + sc.next() + "' se ne može protumačiti kao broj.");
			}
		}

	}

	/**
	 * Prints the calculated perimeter and surface of the rectangle.
	 *
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 */
	public static void printResult(double width, double height) {
		System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height,
				calculateArea(width, height), calculatePerimeter(width, height));

	}

	/**
	 * Calculates the perimeter of the rectangle.
	 *
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 * @return Perimeter of the rectangle
	 */
	public static double calculatePerimeter(double width, double height) {
		if (width <= 0 || height <= 0) {
			return 0;
		} else {
			return 2 * (width + height);
		}

	}

	/**
	 * Calculates the area of the rectangle.
	 *
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 * @return Perimeter of the rectangle
	 */
	public static double calculateArea(double width, double height) {
		if (width <= 0 || height <= 0) {
			return 0;
		} else {
			return width * height;
		}

	}

}
