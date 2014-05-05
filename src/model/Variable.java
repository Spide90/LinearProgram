package model;

public class Variable {

	public String name;
	public float lowerBound = 0;
	public boolean lowerIsInfinity = false;
	public float upperBound = 0;
	public boolean upperIsInfinity = true;
	public final VariableType type = VariableType.SEMI;

	public Variable(String name) {
		this.name = name;
	}

	public Variable(String name, float lowerBound, boolean lowerIsInfinity,
			float upperBound, boolean upperIsInfinity) {
		this.name = name;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.lowerIsInfinity = lowerIsInfinity;
		this.upperIsInfinity = upperIsInfinity;
	}

}
