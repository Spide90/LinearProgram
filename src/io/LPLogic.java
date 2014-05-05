package io;

import java.util.Iterator;
import java.util.ListIterator;

import model.Comparator;
import model.Constraint;
import model.LPProgram;
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
	
	/**
	 * Test whether a Variable is a slack Variable. A Slack Variable is used
	 * to change a LEQ - constraint into a EQ-constraint. (a + b <= c --> a + b + s = c)
	 * 
	 * @param v
	 * @return
	 */
	private static boolean isSlackVariable(Variable v, LPProgram prog){
		if (prog.objective.function.usesVariable(v)) return false;
		Constraint occurence = null;
		for (Constraint c : prog.constraints) {
			if (c.terms.usesVariable(v)) {
				if (occurence == null) {
					occurence = c;
				} else {
					//FIXME case Ax + y = 0, Bx + y = 0 for A==B
					return false;
				}
			}
		}
		if (occurence.comparator != Comparator.EQ) return false;
		if (occurence.comparator == Comparator.LEQ) {
			//TODO continue
		}
		return true;
	}
}
