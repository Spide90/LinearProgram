package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class LPProgram {
	
	public Objective objective = null;
	public LinkedList<Constraint> constraints = new LinkedList<Constraint>();
	public HashMap<String, Variable> variables = new HashMap<String,Variable>();
	
	@Override
	public String toString() {
		return "objective: " + objective.toString() + " constraints: " + constraints.toString();
	}
	
	public void addVariable (Variable var) {
		if (variables.containsKey(var.name)) {
			throw new IllegalStateException("Variable allready defined, do better Goldi!");
		}
		variables.put(var.name, var);
	}
	
	public void removeVariable(String name) {
		variables.remove(name);
	}

	public Variable getVariable (String name) {
		return variables.get(name);
	}
	
	public boolean variableIsDefined(String name) {
		return variables.containsKey(name);
	}
	
	/**
	 * 
	 * @return A copy of the original Program, that can be altered without second thoughts!
	 */
	public LPProgram getCopy(){
		LPProgram newProgram = new LPProgram();
		for (Entry<String,Variable> e : variables.entrySet()) {
			newProgram.addVariable(e.getValue());
			System.out.println("DEBUG - ("+e.getKey() + e.getValue()+ ")");
		}
		for (Constraint c : constraints) {
			newProgram.constraints.add(c.getCopyForProgram(newProgram));
		}	
		newProgram.objective = objective.getCopyForProgram(newProgram);
		return newProgram;
	}
}
