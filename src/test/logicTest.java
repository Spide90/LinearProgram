package test;

import static org.junit.Assert.*;
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
	}

	@Test
	public void testReadAndWrite() {
		LPReader reader = new LPReader(new File("testFile.lp"));
		LPProgram readLpProgram = reader.getLP();
		LPLogic logic = new LPLogic(readLpProgram);
		LPWriter writer = new LPWriter("logicTest1.lp");
		writer.writeProgram(logic.leqOnlyConstraints());
		writer.close();
	}

}
