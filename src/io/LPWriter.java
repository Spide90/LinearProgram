package io;

import java.io.PrintWriter;
import java.util.Map.Entry;

import model.Constraint;
import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.TermList;
import model.Variable;

public class LPWriter {

	private PrintWriter writer;
	int labelId = 0;
	
	public LPWriter(String target) {
		try {
			writer = new PrintWriter(target, "UTF-8");
		} catch (Exception e) {
			System.out.println("UTF-8 is not supported? Thats bad...Yo should change that!");
		}
	}
	
	public void writeProgram(LPProgram program) {
		writeObjective(program.objective);
		writer.println("Subject To");
		for (Constraint c : program.constraints) {
			writeConstraint(c);
		}
		writer.println("Bounds");
		for (Entry<String,Variable> e : program.variables.entrySet()) {
			writeBound(e.getValue());
		}
	}
	
	public void close(){
		writer.close();
	}
	
	public void writeObjective(Objective objective) {
		writer.println(objective.head == Header.MAX ? "Maximize" : "Minimize");
		writeTermList(objective.function);
		writer.println();
	}
	
	public void writeBound(Variable var){
		if (!var.lowerIsInfinity && var.lowerBound == 0 && var.upperIsInfinity) return;
		if (var.lowerIsInfinity && var.upperIsInfinity) {
			writer.print(var.name + " free");
		}
		if (!(!var.lowerIsInfinity && var.lowerBound == 0)) {
			writer.write((var.lowerIsInfinity ? "-Inf" : Float.valueOf(var.lowerBound)) + " <= ");
		} 
		writer.write(var.name);
		if (!var.upperIsInfinity) {
			writer.write(" <= "+(var.lowerIsInfinity ? "-Inf" : Float.valueOf(var.lowerBound)));
		}
		writer.println();
	}
	
	public void writeConstraint(Constraint c) {
		writer.print("c"+Integer.toString(labelId++) + " : ");
		writeTermList(c.terms);
		switch (c.comparator) {
		case EQ:
			writer.print(" = ");
			break;
		case LEQ:
			writer.print(" <= ");
			break;
		case GEQ:
			writer.print(" >= ");
			break;
		default:
			break;
		}
		writer.print(c.constant);
		writer.println();
	}
	
	public void writeTermList(TermList list) {
		boolean first = true;
		for (Term t : list.list) {
			if (first) {
				writer.print(t.constant + " " + t.variable + " ");
				first = false;
				continue;
			}
			writer.print((t.constant < 0 ?" -":" +") + " " + Math.abs(t.constant) + " "+t.variable);
		}
	}
	
}
