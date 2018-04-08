package hr.fer.zemris.java.hw05.collections;

import static java.lang.Math.log10;

public class SimpleHashtable<K, V> {

	public static final int DEFAULT_TABLE_SIZE = 16;
	public static final double LOG10_2 = log10(2);

	private TableEntry<K, V>[] table;

	@SuppressWarnings("unchecked")
	public SimpleHashtable()
	{
		table = (TableEntry<K, V>[]) new Object[DEFAULT_TABLE_SIZE];
	}

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int tableSize) {
		tableSize=normalizeTableSize(tableSize);
		
		table = (TableEntry<K, V>[]) new Object[tableSize];
	}

	private int normalizeTableSize(int tableSize) {
		while(log2(tableSize) != (int) log2(tableSize)) {
			tableSize++;
		}
		
		return tableSize;
	}

	private double log2(int tableSize) {
		return log10(tableSize)/LOG10_2;
	}

	public static class TableEntry<K, V> {

	}

}
