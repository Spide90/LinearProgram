package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import model.Comparator;
import model.Constraint;
import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.TermList;
import model.Variable;

/**
 * reads a linear program from a file this is the more complicated way because
 * we don't use a tree structure because helge said we don't need it ...
 * 
 */
public class LPReader {

	private BufferedReader reader;
	private LPProgram lp;

	public LPReader(File file) {
		try {
			Reader input = new FileReader(file);
			reader = new BufferedReader(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		readLP();
	}

	private void readLP() {
		lp = new LPProgram();
		while (true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null)
				break;
			String[] tokens = line.split("\\s+");
			for (String token : tokens) {
				switch (token.toLowerCase()) {
				case "maximize":
					createObjective(Token.MAX);
					break;
				case "minimize":
					createObjective(Token.MIN);
					break;
				case "minimum":
					createObjective(Token.MIN);
					break;
				case "maximum":
					createObjective(Token.MAX);
					break;
				case "min":
					createObjective(Token.MIN);
					break;
				case "max":
					createObjective(Token.MAX);
					break;
				default:
					break;
				}
			}
		}
	}

	private void createObjective(Token tok) {
		if (!(tok.equals(Token.MAX) || tok.equals(Token.MIN))) {
			throw new IllegalArgumentException(
					"expected min or max but found: " + tok);
		}
		Header header;
		if (tok.equals(Token.MAX)) {
			header = Header.MAX;
		} else {
			header = Header.MIN;
		}
		// read objective function
		TermList termList = new TermList();
		while (true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null) {
				break;
			}
			String[] tokens = line.split("\\s+");
			Objective objective;
			Term term = null;
			Variable variable = null;
			boolean negative = false;
			Number number = null;
			for (String token : tokens) {
				switch (token.toLowerCase()) {
				case "subject":
					objective = new Objective(termList, header);
					lp.objective = objective;
					createConstraints();
					break;
				case "such":
					objective = new Objective(termList, header);
					lp.objective = objective;
					createConstraints();
					break;
				case "st":
					objective = new Objective(termList, header);
					lp.objective = objective;
					createConstraints();
					break;
				case "s.t.":
					objective = new Objective(termList, header);
					lp.objective = objective;
					createConstraints();
					break;
				case "+":
					break;
				case "-":
					negative = true;
					break;
				default:
					// some kind of expression
					try {
						// int?
						int literal = Integer.parseInt(token);
						if (negative)
							literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					try {
						// double?
						double literal = Double.parseDouble(token);
						if (negative)
							literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					// must be a variable
					if (!lp.variableIsDefined(token)) {
						variable = new Variable(token);
						lp.addVariable(variable);
					} else {
						variable = lp.getVariable(token);
					}
					if (number == null) {
						if (negative) {
							number = -1;
						} else {
							number = 1;
						}
						negative = false;
						term = new Term(variable, number.floatValue());
					} else {
						term = new Term(variable, number.floatValue());
					}
					termList.list.add(term);
					number = null;
					term = null;
					variable = null;
					break;
				}
			}
		}
	}

	private void createConstraints() {
		System.out.println(lp);
		while(true) {
			TermList termList = new TermList();
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null) {
				break;
			}
			String[] tokens = line.split("\\s+");
			Term term = null;
			Variable variable = null;
			boolean negative = false;
			Number number = null;
			Comparator comparator = null;
			for (String token : tokens) {
				switch (token.toLowerCase()) {
				case "bounds":
					createBounds();
					break;
				case "+":
					break;
				case "-":
					negative = true;
					break;
				case "=":
					comparator = Comparator.EQ;
					break;
				case "<=":
					comparator = Comparator.LEQ;
					break;
				case ">=":
					comparator = Comparator.GEQ;
					break;
				default:
					//some kind of expression
					try {
						//int?
						int literal = Integer.parseInt(token);
						if (negative) literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					try {
						//double?
						double literal = Double.parseDouble(token);
						if (negative) literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					//must be a variable
					if (token.endsWith(":")) {
						//this is a constraint name... we have no field for the names ;)
						continue;
					}
					if (!lp.variableIsDefined(token)) {
						variable = new Variable(token);
						lp.addVariable(variable);
					} else {
						variable = lp.getVariable(token);
					}
					if (number == null) {
						if (negative) {
							number = -1;
						} else {
							number = 1;
						}
						negative = false;
						term = new Term(variable, number.floatValue());
					} else {
						term = new Term(variable, number.floatValue());
					}
					termList.list.add(term);
					number = null;
					term = null;
					variable = null;
					break;
				}
			}
			if (number != null) {
				Constraint constraint = new Constraint(termList, number.floatValue(), comparator);
				lp.constraints.add(constraint);
			}
		}
	}

	private void createBounds() {
		System.out.println(lp);
		while(true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null) {
				break;
			}
			String[] tokens = line.split("\\s+");
			Variable variable = null;
			boolean negative = false;
			Number number = null;
			Comparator comparator = null;
			for (String token : tokens) {
				switch (token.toLowerCase()) {
				case "end":
					return;
				case "+":
					break;
				case "-":
					negative = true;
					break;
				case "=":
					comparator = Comparator.EQ;
					break;
				case "<=":
					comparator = Comparator.LEQ;
					break;
				case ">=":
					comparator = Comparator.GEQ;
					break;
				case "inf":
					number = Float.MAX_VALUE;
					break;
				case "infinity":
					number = Float.MAX_VALUE;
					break;
				default:
					//some kind of expression
					try {
						//int?
						int literal = Integer.parseInt(token);
						if (negative) literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					try {
						//double?
						double literal = Double.parseDouble(token);
						if (negative) literal *= -1;
						negative = false;
						number = literal;
						break;
					} catch (NumberFormatException e) {
					}
					//must be a variable
					if (token.endsWith(":")) {
						//this is a constraint name... we have no field for the names ;)
						continue;
					}
					if (!lp.variableIsDefined(token)) {
						variable = new Variable(token);
						lp.addVariable(variable);
					} else {
						variable = lp.getVariable(token);
					}
					if (comparator.equals(Comparator.GEQ)) {
						if (number.floatValue() == Float.MAX_VALUE) {
							variable.lowerIsInfinity = true;
						} else {
							variable.lowerBound = number.floatValue();
						}
					} else {
						if (number.floatValue() == Float.MAX_VALUE) {
							variable.lowerIsInfinity = true;
						} else {
							variable.upperBound = number.floatValue();
						}
					}
					number = null;
					variable = null;
					break;
				}
			}
		}
	}
}
