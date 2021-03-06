package model;

/**
 * A Term is part of an expression. It consist of an Variable and a
 * Constant(potential negative) (e.g. 3x, -4y, z)
 * 
 */
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

	@Override
	public String toString() {
		return Float.toString(constant) + variable;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Term))
			return false;
		Term nT = (Term) obj;
		return nT.variable.equals(variable) && (nT.constant == constant);
	}
}
