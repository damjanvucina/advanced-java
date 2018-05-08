package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	public static final String DONE = "done";
	public static List<Complex> list = new LinkedList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		String input;
		int currentNumber = 1;

		while (true) {
			System.out.print("Root " + String.valueOf(currentNumber) + "> ");
			if (sc.hasNextLine()) {
				input = sc.nextLine().trim();

				if (input.equalsIgnoreCase(DONE)) {
					if (list.size() >= 2) {
						break;
					} else {
						System.out.println("You must enter at least two roots, roots entered: " + list.size());
						continue;
					}

				} else {
					input = input.replace(" ", "");
					list.add(parse(input));
				}
			}
			currentNumber++;
		}

		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");

		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(list.toArray(new Complex[list.size()]));
		FractalViewer.show(new FractalProducer(rootedPolynomial));
	}
	
	public static Complex parse(String input) {
		if (input.endsWith("i")) {
			input += "1";
		}

		int currentIndex = 0;
		input = input.replace(" ", "");
		int length = input.length();
		char[] array = input.toCharArray();

		StringBuilder real = null;
		if (!input.startsWith("i")) {
			real = new StringBuilder();
			while (currentIndex < length && (array[currentIndex] != '+' && array[currentIndex] != '-')
					|| currentIndex == 0) {
				real.append(array[currentIndex++]);
			}
		}

		StringBuilder imaginary = null;
		char imaginaryOperator = 0;
		if (input.contains("i")) {
			imaginaryOperator = (input.indexOf("i") == 0) ? '+' : array[input.indexOf("i") - 1];
			currentIndex = input.indexOf("i") + 1;
			imaginary = new StringBuilder();
			while (currentIndex < length) {
				imaginary.append(array[currentIndex++]);
			}
		}
		String sReal = (real == null ? "0" : real.toString());

		String sImaginary = null;
		if (imaginary == null) {
			sImaginary = "0";
		} else {
			sImaginary = (imaginaryOperator == '-' ? "-" + imaginary.toString() : imaginary.toString());
		}

		return new Complex(Double.parseDouble(sReal), Double.parseDouble(sImaginary));
	}
}
