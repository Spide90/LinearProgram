package test;

import static org.junit.Assert.*;
import io.LPWriter;

import java.util.LinkedList;

import model.Comparator;
import model.Constraint;
import model.Header;
import model.LPProgram;
import model.Objective;
import model.Term;
import model.TermList;
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
		Term termX2 = new Term(varX,3);
		Term termY = new Term(varY,-2);
		Term termZ = new Term(VarZ, 5);
		
		LinkedList<Term> exp1 = new LinkedList<Term>();
		LinkedList<Term> exp2 = new LinkedList<Term>();
		LinkedList<Term> exp3 = new LinkedList<Term>();
		
		exp1.add(termX1);
		exp1.add(termY);
		TermList t1 = new TermList(exp1); 
		
		exp2.add(termX2);
		TermList t2 = new TermList(exp2);
		
		exp3.add(termZ);
		TermList t3 = new TermList(exp3);
		
		Constraint c1 = new Constraint(t2,5,Comparator.GEQ);
		Constraint c2 = new Constraint(t3, 0, Comparator.LEQ);
		
		LinkedList<Constraint> cs = new LinkedList<Constraint>();
		cs.add(c1);
		cs.add(c2);
		
		testProgram.objective = new Objective(t1, Header.MAX);
		testProgram.constraints = cs;
		
		System.out.println("TestProgram 1: ");
		System.out.println(testProgram);
	}

	@Test
	public void test() {
		LPWriter writer = new LPWriter("writeTest1.lp");
		writer.writeProgram(testProgram);
		writer.close();
	}

}
