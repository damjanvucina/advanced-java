package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static java.lang.Math.sqrt;

public class PrimesCollection implements Iterable<Integer> {

	private int numOfPrimes;

	public PrimesCollection(int numOfPrimes) {
		if (numOfPrimes < 1) {
			throw new IllegalArgumentException("Collection cannot have less than one number, was: " + numOfPrimes);
		}
		this.numOfPrimes = numOfPrimes;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(numOfPrimes);
		
		for(Integer prime : this) {
			sb.append(prime).append(" ");
		}
		
		sb.delete(sb.lastIndexOf(" "), sb.length());
		return sb.toString();
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeCollectionIterator();
	}

	private class PrimeCollectionIterator implements Iterator<Integer> {
		private int primesProcessed;
		private int lastPrime = 1;

		@Override
		public boolean hasNext() {
			return primesProcessed < numOfPrimes;
		}

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

	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(-3);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
