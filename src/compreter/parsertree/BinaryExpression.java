package compreter.parsertree;

import compreter.Symbol;

public class BinaryExpression extends Tree {
	Tree operandOne= null,
			operandTwo = null;
	Symbol operator=null;
	String labelTrue = null, labelFalse = null;
	
	public BinaryExpression(Tree operandOne, Symbol operator, Tree operandTwo){
		this.operandOne = operandOne;
		this.operator = operator;
		this.operandTwo = operandTwo;
		this.place = Tree.getNextTemp();
		
	}
	
	public String toString(){
		return operandOne.toString() + operator.getValue() + operandTwo.toString();
	}
	
	public String getCode(){
		return this.operandOne.getCode() + 
				this.operandTwo.getCode() +
				this.printLineNumber(true) + 
				this.place + " := " + this.operandOne.place + " " + this.operator.getValue() + " " + this.operandTwo.place
				+ "\n";
	}
	
	public String getLabelCode(){
		return this.getCode();
	}
	
	public String getSimpleCode(){
		return this.getCode();
	}

	public int tLineCount(){
		return this.operandOne.tLineCount() + this.operandTwo.tLineCount() + 1;
	}
}
