package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * 
 * The LLprogram represents the entire Linear Program. The components are:
 * 
 * <ul>
 * <li>The Objective Function.</li>
 * <li>A List of constraints</li>
 * <li>A Hash Map of all variables</li>
 * </ul>
 */
public class LPProgram {

	public Objective objective = null;
	public LinkedList<Constraint> constraints = new LinkedList<Constraint>();
	public HashMap<String, Variable> variables = new HashMap<String, Variable>();

	@Override
	public String toString() {
		return "objective: " + objective.toString() + " constraints: "
				+ constraints.toString();
	}

	/**
	 * Has to be used to add a variable to the helper structures variables. Has
	 * to be kept consistence during the initiallization.
	 */
	public void addVariable(Variable var) {
		if (variables.containsKey(var.name)) {
			throw new IllegalStateException(
					"Variable allready defined, do better Goldi!");
		}
		variables.put(var.name, var);
	}

	public void removeVariable(String name) {
		variables.remove(name);
	}

	public Variable getVariable(String name) {
		return variables.get(name);
	}

	public boolean variableIsDefined(String name) {
		return variables.containsKey(name);
	}

	public HashMap<String, LinkedList<Constraint>> getPostingList() {
		HashMap<String, LinkedList<Constraint>> map = new HashMap<String, LinkedList<Constraint>>();
		for (Entry<String, Variable> e : variables.entrySet()) {
			map.put(e.getKey(), new LinkedList<Constraint>());
		}
		for (Constraint c : constraints) {
			for (Term t : c.terms.list) {
				map.get(t.variable.name).add(c);
			}
		}
		return map;
	}

	/**
	 * Finds one occurrence of the Variable v.
	 * 
	 * @param v
	 * @return
	 */
	public Constraint findVariable(Variable v) {
		for (Constraint c : constraints) {
			if (c.terms.usesVariable(v))
				return c;
		}
		return null;
	}

	/**
	 * @return A copy of the original Program, that can be altered without
	 *         second thoughts! Sorry Christian... I thought that this is
	 *         easier... also I had already written most of it when you said,
	 *         that there is an easier way^^
	 */
	public LPProgram getCopy() {
		LPProgram newProgram = new LPProgram();
		for (Entry<String, Variable> e : variables.entrySet()) {
			newProgram.addVariable(e.getValue());
			System.out.println("DEBUG - (" + e.getKey() + e.getValue() + ")");
		}
		for (Constraint c : constraints) {
			newProgram.constraints.add(c.getCopyForProgram(newProgram));
		}
		newProgram.objective = objective.getCopyForProgram(newProgram);
		return newProgram;
	}

}
