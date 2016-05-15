package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Model {

	public Model() {
		
		mListOfStates = new ArrayList<State>();
		mProbabilitiesOfTransition = new HashMap<State[], Double>();
	}
	
	public static double getProbabilityOfTransition(State s1, State s2) {
		State[] s = {s1, s2};

		for (Map.Entry<State[], Double> m : mProbabilitiesOfTransition.entrySet()) {
			
			if (m.getKey()[0]==s1 && m.getKey()[1]==s2) {
				return m.getValue();
			}
		}
		return 0.0;
	}
	
	public void addState(State s) {
		mListOfStates.add(s);
	}
	
	public void addProbabilityOfTransition(State s1, State s2, double prob) {
		State[] s = {s1, s2};
		mProbabilitiesOfTransition.put(s, prob);
	}
	
	
	public void readFromFile() {
		try {
			Scanner fileIn = new Scanner(new File("src/hmm.txt"));

			int numberOfStates = fileIn.nextInt();
			int numberOfSignalsPerState = fileIn.nextInt();
			String temps;
			double tempd;

			fileIn.nextLine();
			
			for (int i = 0; i < numberOfStates; i++) {
				temps = fileIn.next();
				tempd = Double.parseDouble(fileIn.next());
				State s = new State(temps.charAt(0), tempd);
				for (int j = 0; j < numberOfSignalsPerState; j++) {
					temps = fileIn.next();
					tempd = Double.parseDouble(fileIn.next());
					
					s.addSignal(temps.charAt(0), tempd);
				}		
				mListOfStates.add(s);
			}

			fileIn.nextLine();
			
			for (int i = 0; i < numberOfStates*numberOfStates; i++) {

				addProbabilityOfTransition(findStateInList(fileIn.next().charAt(0)),
						findStateInList(fileIn.next(".").charAt(0)),
						Double.parseDouble(fileIn.next()));	
			}
			
			printModel();
			
			fileIn.close();
			System.out.println("\nReading model successful");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void logarithmize2() {
		
		for (Map.Entry<State[], Double> m : mProbabilitiesOfTransition.entrySet()) {
			
			m.setValue(Math.log10(m.getValue())/Math.log10(2.0));
		}
		for (State s : mListOfStates) {
			s.logarithmize2();
		}
		printModel();
	}
	
	public void fillTableForSequence(String seq) {
		
		mProbTable = new ProbTable(mListOfStates, seq);
		
		for (State s : mListOfStates) {
			mProbTable.findProbTableCell(s,	0).setProbability(s.getInitialProbability()+s.getProbability(seq.charAt(0)));
		}
		
		
		for (int i = 1; i < seq.length(); i++) {
			for (State s : mListOfStates) {
				double tempProb = s.getProbability(seq.charAt(i));
				

				tempProb += mProbTable.findMaxAndSetPointer(s, i);
				
				mProbTable.findProbTableCell(s, i).setProbability(tempProb);
			}
			
		}
		
		mProbTable.printProbTable();
		
	}
	
	public String backtrace() {
		
		ProbTableCell temp = mProbTable.findMaxEndCell();
		System.out.println("Score: " + Math.pow(2, temp.getProbability()));
		
		String ret = new String();
		
		
		while(temp!=null) {
			ret += temp.getState();
			temp = temp.getPointer();
		}
		
		ret = new StringBuilder(ret).reverse().toString();
		
		return ret;
	}
	
	
	private void printModel() {
		
		System.out.println(mListOfStates.size());
		System.out.println(mListOfStates);
		
		for (State s : mListOfStates) {
			s.printProbabilities();
			System.out.println();
		}
		
		for (Map.Entry<State[], Double> m : mProbabilitiesOfTransition.entrySet()) {
			System.out.println(m.getKey()[0] + " " + m.getKey()[1] + " " + m.getValue());
		}
		
	}
	
	
	private State findStateInList(char nameOfState) {
		
		for (State s : mListOfStates) {
			if (s.getStateName() == nameOfState) {
				return s;
			}
		}
		return null;
	}
	
	private List<State> mListOfStates;
	private static Map<State[], Double> mProbabilitiesOfTransition;
	private ProbTable mProbTable;
}
