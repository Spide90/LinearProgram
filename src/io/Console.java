package io;

import java.io.File;

import model.LPProgram;

/**
 * Class that handles the user input and contains the main();
 *
 */
public class Console {
	
	public static Boolean DEBUG = false;
	public static Boolean VERBOSE = false;

	public static String fileIn = "defaultIn.lp";
	public static String fileOut = "defaultOut.lp";
	public static String Command = "copy";
	
	public static void main(String[] args) {
		if (args.length < 3) {
			printHelp();
			return;
		}
		Command = args[0];
		fileIn = args[1];
		fileOut = args[2];
		
		if (args.length > 3) {
			for (int i = 3; i < args.length; i++) {
				switch (args[i].toLowerCase()) {
				case "--verbose":
					VERBOSE = true;
					System.out.println("Set VERBOSE == true");
					break;
				case "--debug":
					DEBUG = true;
					System.out.println("Set DEBUG == true");
					break;
				default:
					System.out.println("Unknown parameter "+args[i]);
				}
			}
		}
		
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
		case "toleq":
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
			System.out.println("[ERROR] Unknown Command!");
			printHelp();
		}
	}
	
	public static void printHelp(){
		System.out.println("Command Format is: [command] [fileIn] [fileOut] [Parameter]\n"
				+ "Commands are: \n"
				+ "- copy\n"
				+ "- toLEQ\n"
				+ "- removeSlack\n"
				+ "- removeSlpit\n"
				+ "- toDual \n"
				+ "Parameters are: \n"
				+ "--verbose\n"
				+ "--debug");
	}

}
