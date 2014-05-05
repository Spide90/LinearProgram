package io;

import java.io.PrintWriter;
import java.util.List;

import model.Constraint;
import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.TermList;

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
	}
	
	public void writeObjective(Objective objective) {
		writer.println(objective.head == Header.MAX ? "Maximize" : "Minimize");
		writeTermList(objective.function);
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
				writer.print(t.constant + " " + t.variable);
				first = false;
			}
			writer.print((t.constant < 0 ?"-":"+") + " " + Math.abs(t.constant) + " "+t.variable);
		}
	}
	
}
