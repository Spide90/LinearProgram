package model;

public class Term {
	
	float constant = 1f;
	Variable variable = null;
		
	public Term(Variable variable) {
		this.variable = variable;
	}
	
	public Term(Variable variable, float constant) {
		this.variable = variable;
		this.constant = constant;
	}

}
