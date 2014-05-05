package model;

import java.util.LinkedList;

public class LPProgram {
	
	public Objective objective = null;
	public LinkedList<Constraint> constraints = new LinkedList<Constraint>();
	
	@Override
	public String toString() {
		return "objective: " + objective.toString() + " constraints: " + constraints.toString();
	}

}
