package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProbTable {

	public ProbTable(List<State> states, String seq) {
		
		mTable = new ArrayList<List<ProbTableCell>>();
		
		for (State s : states) {
			mTable.add(new ArrayList<ProbTableCell>());
		}
		int j = 0;
		for (List<ProbTableCell> l : mTable) {
			State s = states.get(j);
			for (int i = 0; i < seq.length(); i++) {
				l.add(new ProbTableCell(s, seq.charAt(i)));
			}
			j++;
		}
		
	}
	
	public ProbTableCell findProbTableCell(State s, int letterNumber) {
		
		for (List<ProbTableCell> l : mTable) {
			if (l.get(0).getState() == s) {
				return l.get(letterNumber);
			}
		}
		return null;
	}

	public double findMaxAndSetPointer(State currState, int letterNumber) {
		
		double ret = Double.NEGATIVE_INFINITY;
		
		for (List<ProbTableCell> l : mTable) {
			
			double tempp = l.get(letterNumber-1).getProbability() +
					Model.getProbabilityOfTransition(l.get(letterNumber-1).getState(), currState);
			if (tempp > ret) {
				ret = tempp;
				findProbTableCell(currState, letterNumber).setPointer(l.get(letterNumber-1)); 
				System.out.println("Setting pointer: " + l.get(letterNumber-1).getProbability() + " at " + currState + " : " + letterNumber);
			}
		}
		
		return ret;
	}
	
	public ProbTableCell findMaxEndCell() {
		
		double max = Double.NEGATIVE_INFINITY;
		ProbTableCell ret = null;
		
		for (List<ProbTableCell> l : mTable) {
			if (l.get(l.size()-1).getProbability() > max) {
				ret = l.get(l.size()-1);
			}
		}
		return ret;	
		
	}
	
	public void printProbTable() {
		
		for (List<ProbTableCell> l : mTable) {
			System.out.println(l);
		}
		
	}
	
	
	public List<List<ProbTableCell>> mTable;
}
