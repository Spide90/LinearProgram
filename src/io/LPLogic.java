package io;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;

import model.Comparator;
import model.Constraint;
import model.LPProgram;
import model.Term;
import model.Variable;

public class LPLogic {

	LPProgram source;
	
	public LPLogic(LPProgram source) {
		this.source = source;
	}
	
	public LPProgram leqOnlyConstraints(){
		LPProgram toAlter = source.getCopy();
		ListIterator<Constraint> iter = toAlter.constraints.listIterator();
		Constraint c, c_;
		while (iter.hasNext()) {
			c = iter.next();
			switch (c.comparator) {
			case EQ:
				c.comparator = Comparator.LEQ;
				c_ = c.getCopyForProgram(source);
				c_.terms.negate();
				iter.add(c_);
				break;
			case GEQ:
				c.constant = -c.constant;
				c.terms.negate();
				c.comparator = Comparator.LEQ;
				break;
			default:
				break;
			}
			
		}
		return toAlter;
	}
	
	public LPProgram removeSlackVariables() {
		LPProgram toAlter = source.getCopy();
		for (Entry<String,Variable> e : source.variables.entrySet()) {
			if (isSlackVariable(e.getValue(),toAlter)) {
				Constraint c = toAlter.findVariable(e.getValue());
				ListIterator<Term> iter = c.terms.list.listIterator();
				while (iter.hasNext()) {
					Term t = iter.next();
					if (t.variable.equals(e.getValue())) iter.remove();
					//FIXME check range
					break;
				}
			}
		}
		return toAlter;
	}
	
	public LPProgram toDualForm(){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public LPProgram removeSplitVariables(){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	 * Test whether a Variable is a slack Variable. A Slack Variable is used
	 * to change a UNEQ - constraint into a EQ-constraint. (a + b <= c --> a + b + s = c)
	 * 
	 * @param v
	 * @return
	 */
	private static boolean isSlackVariable(Variable v, LPProgram prog){
		if (prog.objective.function.usesVariable(v)) return false;
		Constraint occurenceConstraint = null;
		Term occurenceTerm = null;
		for (Constraint c : prog.constraints) {
			if (c.terms.usesVariable(v)) {
				if (occurenceConstraint == null) {
					occurenceConstraint = c;
					for (Term t : c.terms.list) {
						if (t.variable.equals(v)) occurenceTerm = t;
					}
				} else {
					//Assume that a Variable that occurse twice is not a slack variable
					//FIXME case Ax + y = 0, Bx + y = 0 for A==B
					return false;
				}
			}
		}
		// Some Sanity Checks
		assert(occurenceConstraint.comparator != null);
		assert(occurenceTerm.constant != 0);
		
		// Test whether the variable is only unnecessary or a slack Variable 
		//FIXME tomorrow... this is quite wrong
		if (occurenceConstraint.comparator != Comparator.EQ) return false;
		if (occurenceConstraint.comparator == Comparator.LEQ) {
			return (occurenceTerm.constant > 0);
		} else {
			return (occurenceTerm.constant < 0);
		}
	}
}
