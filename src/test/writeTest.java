package test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import model.LPProgram;
import model.Term;
import model.Variable;

import org.junit.Before;
import org.junit.Test;

public class writeTest {

	LPProgram testProgram;
	
	@Before
	public void setUp() throws Exception {
		testProgram = new LPProgram();
		Variable varX = new Variable("x");
		Variable varY = new Variable("Y",-2f,false,20f,false);
		Variable VarZ = new Variable("Z",1,true,1,true);
		Term termX1 = new Term(varX);
		Term termX2 = new Term(varX,-3);
		Term termY = new Term(varY,2);
		Term termZ = new Term(VarZ, 5);
		
		LinkedList<Term> exp1 = new LinkedList<Term>();
		LinkedList<Term> exp2 = new LinkedList<Term>();
		LinkedList<Term> exp3 = new LinkedList<Term>();
		
		exp1.add(termX1);
		exp1.add(termY);
		
		exp2.add(termX2);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
