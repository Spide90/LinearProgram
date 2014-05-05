package model;

public class Objective {
	
	public TermList function = null;
	public Header head = Header.MIN;
	
	public Objective(TermList function, Header head) {
		this.head = head;
		this.function = function;
	}
	
	@Override
	public String toString() {
		return head + " " + function;
	}

}
