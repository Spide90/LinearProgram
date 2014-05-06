package model;

public class Objective {
	
	public Expression function = null;
	public Header head = Header.MIN;
	
	public Objective(Expression function, Header head) {
		this.head = head;
		this.function = function;
	}
	public Objective getCopyForProgram(LPProgram prog) {
		return new Objective(function.getCopyForProgram(prog), this.head);
	}
	
	@Override
	public String toString() {
		return head + " " + function;
	}

}
