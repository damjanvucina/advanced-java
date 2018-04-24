package hr.fer.zemris.java.hw06.crypto;

import java.util.Scanner;

public class Crypto {
	public static final String START="start";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input=START; 
		String keyText;
		
		while(!input.equalsIgnoreCase("exit")) {
			System.out.println("Please provide expected sha-256 digest for hw06part2.pdf:");
			
			if(sc.hasNextLine()) {
				keyText = sc.nextLine().trim();
			}
		}
		
		
		
	}

}
