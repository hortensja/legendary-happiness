package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {
	
	public static void main(String[] args) {
		
		Model model = new Model();
		model.readFromFile();
		model.logarithmize2();
		
		String seq = new String();
		
		Scanner fileIn;
		try {
			fileIn = new Scanner(new File("src/sequence.txt"));
			seq = fileIn.next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(seq);
		
		model.fillTableForSequence(seq);
		
		System.out.println(model.backtrace());
		
	}
}
