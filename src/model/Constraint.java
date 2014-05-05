package model;

public class Constraint {
	
	public TermList terms = null;
	public float constant = 0;
	public Comparator comparator = Comparator.EQ;
	
	public Constraint(TermList terms, float constant, Comparator comparator) {
		this.terms = terms;
		this.constant = constant;
		this.comparator = comparator;
	}
	
	@Override
	public String toString() {
		return "constraint: " + terms + " " + comparator + " " + constant + "\n";
	}
}
