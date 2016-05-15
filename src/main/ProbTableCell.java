package main;

public class ProbTableCell {

	public ProbTableCell(State s, char c) {
		mState = s;
		mSeqLetter = c;
	}
	
	public void setProbability(double p) {
		mProbability = p;
	}
	
	public double getProbability() {
		return mProbability;
	}
	
	public void setPointer(ProbTableCell c) {
		mPointer = c;
	}
	
	public ProbTableCell getPointer() {
		return mPointer;
	}
	
	public State getState() {
		return mState;
	}
	
	public char getLetter() {
		return mSeqLetter;
	}
	
	
	@Override
	public String toString() {
		return mState.toString() + " " + mSeqLetter + " " + mProbability;
		
	}
	
	private double mProbability;
	private State mState;
	private char mSeqLetter;
	private ProbTableCell mPointer;
}
