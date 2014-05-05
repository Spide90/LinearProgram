package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TermList {
	
	public LinkedList<Term> list;
	
	public TermList() {
		list = new LinkedList<Term>();
	}
	
	public TermList(LinkedList<Term> list) {
		this.list = list;
	}
	
	public List<Variable> getVariables(){
		LinkedList<Variable> result = new LinkedList<Variable>();
		for (Term t : list) {
			result.add(t.variable);
		}
		return result;
	}
	
	public Iterator<Term> iterator(){
		return list.iterator();
	}
	
	public boolean usesVariable(Variable v) {
		for (Term t : list) {
			if (t.variable.equals(v)) return true;
		}
		return false;
	}
	
	public void negate() {
		for (Term t : list) {
			t.constant = -t.constant;
		}
	}
	
	public TermList getCopyForProgram(LPProgram prog) {
		TermList newList = new TermList();
		for (Term t : list) {
			newList.list.add(new Term(prog.getVariable(t.variable.name),t.constant));
		}
		return newList;
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
}
