package hr.fer.zemris.java.hw05.collections;

import java.util.Iterator;

/**
 * The class that contains some code snippets from this homework assignment
 * papers. They are left here for possible further testing, but are also
 * available in PrimjerTest class in a slightly different manner. Some of the
 * code snippets are commented out since they throw exceptions or would mess up
 * other snippets.
 * 
 * @author Damjan Vučina
 */
public class Primjer {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments of the main method. Notice: they are not used here.
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println("------------------------------------------");

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}

		System.out.println("------------------------------------------");

		// Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
		// examMarks.iterator();
		// while (iter.hasNext()) {
		// SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
		// if (pair.getKey().equals("Ivana")) {
		// iter.remove(); // sam iterator kontrolirano uklanja trenutni element
		// }
		// }

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println("------------------------------------------");

		// Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
		// examMarks.iterator();
		// while (iter.hasNext()) {
		// SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
		// if (pair.getKey().equals("Ivana")) {
		// iter.remove();
		// iter.remove();
		// }
		// }

		// Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
		// examMarks.iterator();
		// while (iter.hasNext()) {
		// SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
		// if (pair.getKey().equals("Ivana")) {
		// examMarks.remove("Ivana");
		// }
		// }

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());

	}
}
