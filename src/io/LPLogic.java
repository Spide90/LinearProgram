package io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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

	public LPProgram leqOnlyConstraints() {
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
	 * * Test whether a Variable is a slack Variable. A Slack Variable is used
	 * to change a UNEQ - constraint into a EQ-constraint. (a + b <= c --> a + b
	 * + s = c) We assume the following properties:
	 * 
	 * <ul>
	 * <li>Slack Variables are not contained in the objective Function</li>
	 * <li>Slack Variables occurs in only 1 constraint. (Assume there are no
	 * equal constraint)</li>
	 * <li>Slack Variables are either bounded by [-inf,0] or [0,inf]</li>
	 * <li>Constant Factors in Terms are != 0
	 * </ul>
	 * 
	 * @return
	 */
	public LPProgram removeSlackVariables() {
		LPProgram toAlter = source.getCopy();
		HashMap<String, LinkedList<Constraint>> postingMap = toAlter
				.getPostingList();

		for (Entry<String, Variable> e : toAlter.variables.entrySet()) {
			Variable v = e.getValue();

			if (!((v.lowerIsInfinity && !v.upperIsInfinity && v.upperBound == 0) || (!v.lowerIsInfinity
					&& v.upperIsInfinity && v.lowerBound == 0)))
				continue;

			if (source.objective.function.usesVariable(v))
				continue;

			LinkedList<Constraint> postingList = postingMap.get(v.name);
			if (postingList.size() > 1)
				continue;
			if (postingList.size() == 0)
				continue;
			
			Constraint c = postingList.getFirst();
			if (c.comparator != Comparator.EQ)
				continue;
			
			Term t = c.terms.findTerm(v);
			assert (t != null);
			boolean postiveVar = !v.lowerIsInfinity
					&& v.upperIsInfinity && v.lowerBound == 0;
			
			c.terms.removeTerm(t);
			if (postiveVar) {
				if (t.constant > 0) {
					c.comparator = Comparator.LEQ;
				} else {
					c.comparator = Comparator.GEQ;
				}
			} else {
				if (t.constant < 0) {
					c.comparator = Comparator.LEQ;
				} else {
					c.comparator = Comparator.GEQ;
				}
			}

		}
		return toAlter;
	}

	public LPProgram toDualForm() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public LPProgram removeSplitVariables() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
