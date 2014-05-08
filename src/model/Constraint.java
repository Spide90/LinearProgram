package model;

/**
 * 
 * A Constraint consist of a expression (list of terms), a comparator(<=,=>,=)
 * and one constant. (e.g. 3x + y <= 3)
 * 
 */
public class Constraint {

	public Expression terms = null;
	public float constant = 0;
	public Comparator comparator = Comparator.EQ;

	public Constraint(Expression terms, float constant, Comparator comparator) {
		this.terms = terms;
		this.constant = constant;
		this.comparator = comparator;
	}

	/**
	 * @param prog a complete copy of the constraint
	 * @return
	 */
	public Constraint getCopyForProgram(LPProgram prog) {
		return new Constraint(terms.getCopyForProgram(prog), this.constant,
				this.comparator);
	}

	@Override
	public String toString() {
		return "constraint: " + terms + " " + comparator + " " + constant
				+ "\n";
	}
}
