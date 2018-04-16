package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static final int PASS_TRESHOLD = 2;
	private static final int NUM_OF_ELEMENTS = 7;

	private static List<String> lines;
	private static List<StudentRecord> records;
	private static String[] elements;

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
	
	
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
						   .filter(s -> s.getTotalPoints() > 25)
						   .count();
		return broj;
		
	}
	
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj5 = records.stream()
							.filter(s -> s.getGrade() == 5)
							.count();
		//long broj5 = vratiListuOdlikasa(records).size();	
		
		return broj5;
	}
	
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records){
		List<StudentRecord> odlikasi = records.stream()
											  .filter(s -> s.getGrade() == 5)
											  .collect(Collectors.toList());
		return odlikasi;
	}
	
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records){
		List<StudentRecord> odlikasiSortirano = records.stream()
													   .filter(s -> s.getGrade() == 5)
													   .sorted((s1, s2) -> Double.compare(s2.getTotalPoints(), s1.getTotalPoints()))
													   .collect(Collectors.toList());
		return odlikasiSortirano;
	}
	
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records){
		List<String> nepolozeniJMBAGovi = records.stream()
												 .filter(s -> s.getGrade()==1)
												 .map(s -> s.getJmbag())
												 .sorted((s1, s2) -> s1.compareTo(s2))
												 .collect(Collectors.toList());
		return nepolozeniJMBAGovi;
	}
	
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records){
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
																  .collect(Collectors.groupingBy(StudentRecord::getGrade));
		return mapaPoOcjenama;
	}
	
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records){
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
													   .collect(Collectors.toMap(StudentRecord::getGrade,
															   					 s -> 1,
															   					 (s, a) -> s + 1));
		return mapaPoOcjenama2;
	}
	
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records){
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
																  .collect(Collectors.partitioningBy
																		  (s -> s.getGrade() >= PASS_TRESHOLD));
		
		return prolazNeprolaz;
	}
	//@formatter:on

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

	private static boolean isEmptyRow(String[] elements) {
		return elements.length == 1;
	}

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
