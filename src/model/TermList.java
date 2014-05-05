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
}
