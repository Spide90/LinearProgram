package test;

import static org.junit.Assert.*;
import io.Console;
import io.LPLogic;
import io.LPReader;
import io.LPWriter;

import java.io.File;

import model.LPProgram;

import org.junit.Before;
import org.junit.Test;

public class logicTest {

	@Before
	public void setUp() throws Exception {
		Console.VERBOSE = true;
		Console.DEBUG = true;
	}

	@Test
	public void testReadAndWrite() {
		LPReader reader = new LPReader(new File("testLeqOnlyIn.lp"));
		LPProgram readLpProgram = reader.getLP();
		LPLogic logic = new LPLogic(readLpProgram);
		LPWriter writer = new LPWriter("testLeqOnlyOut.lp");
		writer.writeProgram(logic.leqOnlyConstraints());
		writer.close();
	}
	
	@Test
	public void testFindSlack() {
		LPReader reader = new LPReader(new File("testFileSlackIn.lp"));
		LPProgram readLpProgram = reader.getLP();
		LPLogic logic = new LPLogic(readLpProgram);
		LPWriter writer = new LPWriter("testFileSlackOut.lp");
		writer.writeProgram(logic.removeSlackVariables());
		writer.close();
	}

}
