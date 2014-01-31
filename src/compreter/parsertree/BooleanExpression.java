package compreter.parsertree;

import compreter.Symbol;

public class BooleanExpression extends Tree {
	Tree operandOne= null,
			operandTwo = null;
	Symbol operator=null;
	
	public BooleanExpression(Tree operandOne, Symbol operator, Tree operandTwo){
		this.operandOne = operandOne;
		this.operator = operator;
		this.operandTwo = operandTwo;
	}
	
	public String toString(){
		return "(" + operandOne.toString() + " " + operator.getValue() + " " + operandTwo.toString() + ")";
	}
}
