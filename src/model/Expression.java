package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Expression is a list of terms (t1, t2,..., tn) the represented expression
 * is t1 + t2 + ... + tn. Any term is a constant and a variable (eg -2x, 3y or
 * z)
 * 
 */
public class Expression {

	public LinkedList<Term> list;

	public Expression() {
		list = new LinkedList<Term>();
	}

	public boolean removeTerm(Term t) {
		return list.remove(t);
	}

	public Expression(LinkedList<Term> list) {
		this.list = list;
	}

	/**
	 * 
	 * @return A List of all Variables used in the Expression
	 */
	public List<Variable> getVariables() {
		LinkedList<Variable> result = new LinkedList<Variable>();
		for (Term t : list) {
			result.add(t.variable);
		}
		return result;
	}

	public Term findTerm(Variable v) {
		for (Term t : list) {
			if (t.variable.equals(v))
				return t;
		}
		return null;
	}

	public Iterator<Term> iterator() {
		return list.iterator();
	}

	public boolean usesVariable(Variable v) {
		for (Term t : list) {
			if (t.variable.equals(v))
				return true;
		}
		return false;
	}

	public void negate() {
		for (Term t : list) {
			t.constant = -t.constant;
		}
	}

	public Expression getCopyForProgram(LPProgram prog) {
		Expression newList = new Expression();
		for (Term t : list) {
			newList.list.add(new Term(prog.getVariable(t.variable.name),
					t.constant));
		}
		return newList;
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
