package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class State {

	public State(char name, double prob) {
		mProbabilities = new HashMap<Character, Double>();
		mStateName = name;
		mInitialProbability = prob;
	}

	
	public double getProbability(char signal) {
		return mProbabilities.get(signal);
	}
	
	public char getStateName() {
		return mStateName;
	}
	
	public double getInitialProbability() {
		return mInitialProbability;
	}
	
	public void addSignal(char signal, double prob) {
		mProbabilities.put(signal, prob);		
	}
	
	public void logarithmize2() {
		for (Map.Entry<Character, Double> p : mProbabilities.entrySet()) {
			p.setValue(Math.log10(p.getValue())/Math.log10(2.0));
		}
		mInitialProbability = Math.log10(mInitialProbability)/Math.log10(2.0);
	}
	
	public void printProbabilities() {
		System.out.println(mStateName + "\t" + mInitialProbability);
		
		for (Map.Entry<Character, Double> m : mProbabilities.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
		}
	}
	
	@Override
	public String toString() {
		return Character.toString(mStateName);
	}
	
	private char mStateName;
	private double mInitialProbability;
	private Map<Character, Double> mProbabilities;
	
}
