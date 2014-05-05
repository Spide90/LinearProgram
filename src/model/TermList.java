package model;

import java.util.LinkedList;

public class TermList {
	
	LinkedList<Term> list;
	
	public TermList() {
		list = new LinkedList<Term>();
	}
	
	public TermList(LinkedList<Term> list) {
		this.list = list;
	}

}
