package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class used for demonstration of StudentRecord class and for getting
 * familiar with streams.
 * 
 * @author Damjan Vučina
 */
public class StudentDemo {

	/** The Constant PASS_TRESHOLD. */
	public static final int PASS_TRESHOLD = 2;

	/** The Constant NUM_OF_ELEMENTS. */
	private static final int NUM_OF_ELEMENTS = 7;

	/** The lines. */
	private static List<String> lines;

	/** The records. */
	private static List<StudentRecord> records;

	/** The elements. */
	private static String[] elements;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments. Notice: not used here
	 */
	public static void main(String[] args) {

		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Requested file does not exist");
		}

		records = convert(lines);

		System.out.println(vratiBodovaViseOd25(records));
		System.out.println(vratiBrojOdlikasa(records));
		System.out.println(vratiListuOdlikasa(records));
		System.out.println(vratiSortiranuListuOdlikasa(records));
		System.out.println(vratiPopisNepolozenih(records));
		System.out.println(razvrstajStudentePoOcjenama(records));
		System.out.println(vratiBrojStudenataPoOcjenama(records));
		System.out.println(razvrstajProlazPad(records));

	}
	//@formatter:off
	
	
	/**
	 * Returns the number of students who in this school year got a 
	 * total of more than 25 points. Total points consist of midterm
	 * points, final exam points and laboratory exercises points.
	 *
	 * @param records the records
	 * @return the number of students who in this school year got a 
	 * total of more than 25 points.
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
						   .filter(s -> s.getTotalPoints() > 25)
						   .count();
		return broj;
		
	}
	
	/**
	 * Returns the number of students who in this school year were given A grade 
	 * 
	 *
	 * @param records the records
	 * @return the long
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj5 = records.stream()
							.filter(s -> s.getGrade() == 5)
							.count();
		//long broj5 = vratiListuOdlikasa(records).size();	
		
		return broj5;
	}
	
	/**
	 * Returns a list of students who in this school year were given A grade
	 *
	 * @param records the records
	 * @return the list of students who in this school year were given A grade
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records){
		List<StudentRecord> odlikasi = records.stream()
											  .filter(s -> s.getGrade() == 5)
											  .collect(Collectors.toList());
		return odlikasi;
	}
	
	/**
	 * Returns a list of students who in this school year were given A grade, sorted by jmbag
	 *
	 * @param records the records
	 * @return the list of students who in this school year were given A grade, sorted by jmbag
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records){
		List<StudentRecord> odlikasiSortirano = records.stream()
													   .filter(s -> s.getGrade() == 5)
													   .sorted((s1, s2) -> Double.compare(s2.getTotalPoints(), s1.getTotalPoints()))
													   .collect(Collectors.toList());
		return odlikasiSortirano;
	}
	
	/**
	 * list of students who in this school year were given F grade
	 *
	 * @param records the records
	 * @return the list
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records){
		List<String> nepolozeniJMBAGovi = records.stream()
												 .filter(s -> s.getGrade()==1)
												 .map(s -> s.getJmbag())
												 .sorted((s1, s2) -> s1.compareTo(s2))
												 .collect(Collectors.toList());
		return nepolozeniJMBAGovi;
	}
	
	/**
	 * Maps grades to students and returns that map
	 *
	 * @param records the records
	 * @return the map mapping grades to students
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records){
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
																  .collect(Collectors.groupingBy(StudentRecord::getGrade));
		return mapaPoOcjenama;
	}
	
	/**
	 * Maps grades to number of students who were given that grade and returns that map
	 *
	 * @param records the records
	 * @return the map mapping grades to number of students who were given that grade
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records){
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
													   .collect(Collectors.toMap(StudentRecord::getGrade,
															   					 s -> 1,
															   					 (s, a) -> s + a));
		return mapaPoOcjenama2;
	}
	
	/**
	 * Maps students to grades F or anything better than F
	 *
	 * @param records the records
	 * @return the map mapping students to grades F or anything better than F
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records){
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
																  .collect(Collectors.partitioningBy
																		  (s -> s.getGrade() >= PASS_TRESHOLD));
		
		return prolazNeprolaz;
	}
	//@formatter:on

	/**
	 * Reads the input student database file and returns a list of StudentRecords.
	 *
	 * @param lines
	 *            the lines
	 * @return the list of StudentRecords.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> temporary = new LinkedList<>();
		for (String row : lines) {
			elements = row.split("\t");

			if (isEmptyRow(elements)) {
				continue;
			}
			if (elements.length != NUM_OF_ELEMENTS) {
				throw new IllegalArgumentException("Invalid input file format");
			}
			validateElementTypes(elements);
			//@formatter:off
			temporary.add(new StudentRecord(elements[0],
											elements[1],
											elements[2],
											Double.parseDouble(elements[3]),
											Double.parseDouble(elements[4]), 
											Double.parseDouble(elements[5]), 
											Integer.parseInt(elements[6])));
			//@formatter:on
		}
		return temporary;
	}

	/**
	 * Checks if the currently read row is empty
	 *
	 * @param elements
	 *            the elements
	 * @return true, if is empty row
	 */
	private static boolean isEmptyRow(String[] elements) {
		return elements.length == 1;
	}

	/**
	 * Validates element types.
	 *
	 * @param elements
	 *            the elements
	 */
	//@formatter:off
	private static void validateElementTypes(String[] elements) {
		if(!(elements[0] instanceof String &&
			 elements[1] instanceof String &&
			 elements[2] instanceof String )) {
			throw new IllegalArgumentException("Invalid argument type.");
		}
		
		try {
			Double.parseDouble(elements[3]);
			Double.parseDouble(elements[4]);
			Double.parseDouble(elements[5]);
			Integer.parseInt(elements[6]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid argument type.");
		}
	}
	//@formatter:on

}
