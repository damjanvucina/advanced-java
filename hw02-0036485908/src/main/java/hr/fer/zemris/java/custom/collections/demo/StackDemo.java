package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {

		if (args.length != 1) {
			throw new IllegalArgumentException("Program accepts only one argument. You entered " + args.length);
		}

		String[] input = args[0].split(" ");

		ObjectStack stack = new ObjectStack();

		for (String element : input) {
			try {
				stack.push(Integer.parseInt(element));
			} catch (NumberFormatException ex) {
				
				int secondArgument = (Integer) stack.pop();
				int firstArgument = (Integer) stack.pop();
				
				int result = performOperation(firstArgument, secondArgument, element);
				
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			throw new IllegalArgumentException(
					"Stack implementation error. In this iteration stack size cannot be " + stack.size());
		}
		System.out.println("Result is " + stack.pop());
	}

	private static int performOperation(int firstArgument, int secondArgument, String element) {
		switch (element) {
		case "+":
			return firstArgument + secondArgument;

		case "-":
			return firstArgument - secondArgument;

		case "/":
			return (int) (firstArgument / secondArgument);

		case "*":
			return firstArgument * secondArgument;

		case "%":
			return firstArgument % secondArgument;

		default:
			throw new IllegalArgumentException(
					"Operator " + element + " is not supported by this implementation of stack.");
		}
	}
}
