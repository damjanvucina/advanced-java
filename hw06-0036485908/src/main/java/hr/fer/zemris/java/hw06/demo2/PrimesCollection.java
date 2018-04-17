package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static java.lang.Math.sqrt;

/**
 * The class that represents a collection of prime numbers. The constuctor
 * receives the requested number of prime numbers. Numbers are calculated
 * dynamically per user request. This collection can be iterated over in
 * for-each loops.
 * 
 * @author Damjan Vučina
 */
public class PrimesCollection implements Iterable<Integer> {

	/** The number of prime number that need to be calculated. */
	private int numOfPrimes;

	/**
	 * Instantiates a new collection of prime numbers.
	 *
	 * @param numOfPrimes
	 *            The number of prime number that need to be calculated.
	 * 
	 * @throws IllegalArgumentException
	 *             if the size of the collection is to be set to a value less than
	 *             one.
	 */
	public PrimesCollection(int numOfPrimes) {
		if (numOfPrimes < 1) {
			throw new IllegalArgumentException("Collection cannot have less than one number, was: " + numOfPrimes);
		}
		this.numOfPrimes = numOfPrimes;
	}

	/**
	 * Generates a String representation of this collection.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder(numOfPrimes);

		for (Integer prime : this) {
			sb.append(prime).append(" ");
		}

		sb.delete(sb.lastIndexOf(" "), sb.length());
		return sb.toString();
	}

	/**
	 * Returns a new Iterator used for iterating over the elements of this
	 * collection.
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimeCollectionIterator();
	}

	/**
	 * The Class PrimeCollectionIterator that is used for iterationg over the
	 * elements of the collection.
	 */
	private class PrimeCollectionIterator implements Iterator<Integer> {

		/** The number of prime numbers already processed. */
		private int primesProcessed;

		/** The last prime number processed. */
		private int lastPrime = 1;

		/**
		 * Checks if this collection any unprocessed prime numbers.
		 */
		@Override
		public boolean hasNext() {
			return primesProcessed < numOfPrimes;
		}

		/**
		 * Calculates a new prime number.
		 * 
		 * @throws NoSuchElementException
		 *             if all prime numbers have already been calculated
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("All prime numbers have already been calculated");
			}

			for (int current = lastPrime + 1;; current++) {
				if (isPrime(current)) {
					primesProcessed++;
					lastPrime = current;
					return current;
				}
			}
		}

		/**
		 * Checks a number given via argument is a prime number.
		 *
		 * @param number
		 *            the number to be checked for being a prime number
		 * @return true, if is prime
		 */
		private boolean isPrime(int number) {
			if (number != 2 && number % 2 == 0) {
				return false;
			}

			for (int i = 3, root = (int) sqrt(number); i <= root; i++) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}
	}
}
