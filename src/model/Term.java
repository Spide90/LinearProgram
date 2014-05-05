package model;

public class Term {
	
	public float constant = 1f;
	public Variable variable = null;
		
	public Term(Variable variable) {
		this.variable = variable;
	}
	
	public Term(Variable variable, float constant) {
		this.variable = variable;
		this.constant = constant;
	}

}
