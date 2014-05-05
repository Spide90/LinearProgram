package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.TermList;
import model.Variable;

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
		while(true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null) break;
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
			throw new IllegalArgumentException("expected min or max but found: " + tok);
		}
		Header header;
		if (tok.equals(Token.MAX)) {
			header = Header.MAX;
		} else {
			header = Header.MIN;
		}
		//read objective function
		TermList termList = new TermList();
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
			Objective objective;
			Term term = null;
			Variable variable = null;
			boolean negative = false;
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
				default:
					//some kind of expression
					try {
						//int?
						int literal = Integer.parseInt(token);
						if (negative) literal *= -1;
						term = new Term(variable, literal);
						break;
					} catch (NumberFormatException e) {
					}
					try {
						//double?
						double literal = Double.parseDouble(token);
						if (negative) literal *= -1;
						term = new Term(variable, (float) literal);
						break;
					} catch (NumberFormatException e) {
					}
					//must be a variable
					variable = new Variable(token);
					if (term != null && term.variable != null) {
						termList.list.add(term);
					} else {
						term = new Term(variable);
						termList.list.add(term);
					}
					term = null;
					variable = null;
					break;
				}
			}
		}
	}
	
	private void createConstraints() {
		System.out.println("you shall not pass!");
	}
}
