package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import model.Constraint;
import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.Expression;
import model.Variable;

/**
 * Class used to write a LPProgram into a file. has to be closed with close();
 * 
 */
public class LPWriter {

	private PrintWriter writer;
	private String target = "defaultTarget.lp";
	int labelId = 0;

	public LPWriter(String target) {
		this.target = target;
		try {
			writer = new PrintWriter(target, "UTF-8");
		} catch (Exception e) {
			System.out
					.println("UTF-8 is not supported? Thats bad...You should change that!");
		}
	}

	public void writeProgram(LPProgram program) {
		writeObjective(program.objective);
		writer.println("Subject To");
		for (Constraint c : program.constraints) {
			writeConstraint(c);
		}
		writer.println("Bounds");
		for (Entry<String, Variable> e : program.variables.entrySet()) {
			writeBound(e.getValue());
		}
		writer.println("END");
	}

	public void close() {
		writer.close();
		if (Console.VERBOSE) {
			System.out.println("__RESULT:");
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(target));
				String line = in.readLine();
				while(line != null)
				{
				  System.out.println(line);
				  line = in.readLine();
				}
				in.close();
			} catch (IOException e) {
				System.out.println("[ERROR] could not print Output...");
			}

		}
	}

	public void writeObjective(Objective objective) {
		writer.println(objective.head == Header.MAX ? "Maximize" : "Minimize");
		writeTermList(objective.function);
		writer.println();
	}

	public void writeBound(Variable var) {
		if (!var.lowerIsInfinity && var.lowerBound == 0 && var.upperIsInfinity)
			return;
		if (var.lowerIsInfinity && var.upperIsInfinity) {
			writer.print(var.name + " free");
			writer.println();
			return;
		}
		if (!(!var.lowerIsInfinity && var.lowerBound == 0)) {
			writer.write((var.lowerIsInfinity ? "-Inf" : constantToString(var.lowerBound)) + " <= ");
		}
		writer.write(var.name);
		if (!var.upperIsInfinity) {
			writer.write(" <= "
					+ (var.upperIsInfinity ? "Inf" : constantToString(var.upperBound)));
		}
		writer.println();
	}

	public void writeConstraint(Constraint c) {
		writer.print("c" + Integer.toString(labelId++) + " : ");
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
		writer.print(c.constant == Math.ceil(c.constant) ? Integer.toString((int) c.constant) : c.constant);
		writer.println();
	}

	public void writeTermList(Expression list) {
		boolean first = true;
		for (Term t : list.list) {
			if (first) {
				writer.print((t.constant == 1f ? "" : (t.constant == -1f ? "-"
						: t.constant == Math.ceil(t.constant) ? Integer
								.toString((int) t.constant) : t.constant))
						+ " " + t.variable + " ");
				first = false;
				continue;
			}
			writer.print((t.constant < 0 ? " -" : " +")
					+ " "
					+ (Math.abs(t.constant) == 1f ? "" : t.constant == Math
							.ceil(t.constant) ? Integer.toString((int) Math
							.abs(t.constant)) : Math.abs(t.constant)) + " "
					+ t.variable);
		}
	}
	
	public String constantToString(float c) {
		return c == Math.ceil(c) ? Integer.toString((int) c) : Float.toString(c);
	}

}
