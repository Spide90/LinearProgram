package model;

import java.util.HashMap;
import java.util.LinkedList;

public class LPProgram {
	
	public Objective objective = null;
	public LinkedList<Constraint> constraints = new LinkedList<Constraint>();
	public HashMap<String, Variable> variables = new HashMap<String,Variable>();
	

}
