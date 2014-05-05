package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class LPReader {
	
	private File file;
	
	public LPReader(File file) {
		this.file = file;
		readLP();
	}
	
	private void readLP() {
		BufferedReader reader = null;
		try {
			Reader input = new FileReader(file);
			reader = new BufferedReader(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(true) {
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line == null) break;
			String[] tokens = line.split("//s+");
			for (String token : tokens) {
				identifyToken(token);
			}
		}
	}

	private void identifyToken(String token) {
		switch (token.toLowerCase()) {
		case "maximize":
			break;
		case "minimize":
			break;
		case "minimum":
			break;
		case "maximum":
			break;
		case "min":
			break;
		case "max":
			break;
		case "subject to":
			break;
		case "such that":
			break;
		case "st":
			break;
		case "s.t.":
			break;
		case "bounds":
			break;
		case "binary":
			break;
		case "binaries":
			break;
		case "bin":
			break;
		case "general":
			break;
		case "generals":
			break;
		case "gen":
			break;
		case "semi-continous":
			break;
		case "semis":
			break;
		case "semi":
			break;
		case "end":
			break;
		default:
			break;
		}
	}
}
