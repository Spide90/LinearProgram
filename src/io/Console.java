package io;

import java.io.File;

import model.LPProgram;

/**
 * Class that handles the user input and contains the main();
 *
 */
public class Console {

	public static String fileIn = "defaultIn.lp";
	public static String fileOut = "defaultOut.lp";
	public static String Command = "copy";
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Command Format is: [command] [fileIn] [fileOut]\n"
					+ "Commands are: \n"
					+ "- copy\n"
					+ "- removeLEQ\n"
					+ "- removeSlack\n"
					+ "- removeSlpit\n"
					+ "- toDual");
			return;
		}
		Command = args[0];
		fileIn = args[1];
		fileOut = args[2];
		
		LPReader reader = new LPReader(new File(fileIn));
		LPWriter writer = new LPWriter(fileOut);
		LPProgram program = reader.getLP();
		LPLogic logic = new LPLogic(program);
		Command = Command.toLowerCase();
		switch (Command) {
		case "copy":
			writer.writeProgram(program);
			writer.close();
			break;
		case "removeleq":
			writer.writeProgram(logic.leqOnlyConstraints());
			writer.close();
			break;
		case "removeslack":
			writer.writeProgram(logic.removeSlackVariables());
			writer.close();
			break;
		case "removesplit":
			writer.writeProgram(logic.removeSplitVariables());
			writer.close();
			break;
		case "todual":
			writer.writeProgram(logic.toDualForm());
			writer.close();
			break;
		default:
			System.out.println("[ERROR] Unkown Command!");
		}
	}

}
