package compreter.parsertree;

import compreter.Symbol;

public class BinaryExpression extends Tree {
	Tree operandOne= null,
			operandTwo = null;
	Symbol operator=null;
	public enum Type{AIRTHMETIC, RELATIONAL, BOOLEAN};
	Type type = null;
	
	String labelTrue = null, labelFalse = null;
	
	public BinaryExpression(Tree operandOne, Symbol operator, Tree operandTwo, Type type){
		this.operandOne = operandOne;
		this.operator = operator;
		this.operandTwo = operandTwo;
		this.type = type;
		this.place = Tree.getNextTemp();
		
		if(this.type == Type.RELATIONAL){
			this.labelTrue = Tree.getNextLabel();
			this.labelFalse = Tree.getNextLabel();
		}
		
	}
	
	public String toString(){
		return operandOne.toString() + operator.getValue() + operandTwo.toString();
	}
	
	public String getCode(){
		switch(this.type){
			case AIRTHMETIC:
				return this.getAirthmeticCode();
			case BOOLEAN:
				return this.getBooleanCode();
			case RELATIONAL:
				return this.getRelationalCode();
		}
		return "";
	}
	
	public String getLabelCode(){
		switch(this.type){
			case AIRTHMETIC:
				return this.getAirthmeticCode();
			case BOOLEAN:
				return this.getBooleanCode();
			case RELATIONAL:
				return this.getRelationalLabelCode();
		}
		return "";
	}
	
	private String getAirthmeticCode(){
		return this.operandOne.getCode() + 
				this.operandTwo.getCode() +
				this.printLineNumber(true) + 
				this.place + " := " + this.operandOne.place + " " + this.operator.getValue() + " " + this.operandTwo.place
				+ "\n";
	}
	
	private String getBooleanCode(){
		return this.operandOne.getCode() + 
				this.operandTwo.getCode() +
				this.printLineNumber(true) + 
				this.place + " := " + this.operandOne.place +
				(this.operator.getValue().compareTo("||") == 0 ? " or " : " and ") + 
				 this.operandTwo.place
				 + "\n";
	}

	private String getRelationalCode(){
		return this.operandOne.getCode() + 
				this.operandTwo.getCode() +
				this.printLineNumber(true) + 
				"if " + this.operandOne.place + " " +  this.operator.getValue() + " " +this.operandTwo.place + 
				" goto := " + String.valueOf(this.currentLineNumber+3) + "\n" + 
				this.printLineNumber(true) +
				this.place + " := false\n" +
				this.printLineNumber(true) +
				"goto := " + String.valueOf(this.currentLineNumber+2) + "\n"+
				this.printLineNumber(true) +
				this.place + " := true\n";
	}
	
	private String getRelationalLabelCode(){
		return this.operandOne.getLabelCode() + 
				this.operandTwo.getLabelCode() +
				this.printLineNumber(true) + 
				" goto := " + this.labelTrue + 
				" if " + this.operandOne.place + " " +  this.operator.getValue() + " " +this.operandTwo.place + "\n" + 
				this.printLineNumber(true) + this.place + " := false\n" +
				this.printLineNumber(true) + "goto := " + this.labelFalse + "\n"+
				this.printLineNumber(true) + "label := "+ this.labelTrue + "\n" + 
				this.printLineNumber(true) + this.place + " := true\n" + 
				this.printLineNumber(true) + "label := "+ this.labelFalse + "\n";
	}

	public int tLineCount(){
		switch(this.type){
			case AIRTHMETIC:
			case BOOLEAN:
				return this.operandOne.tLineCount() + this.operandTwo.tLineCount() + 1;
			case RELATIONAL:
				return this.operandOne.tLineCount() + this.operandTwo.tLineCount() + 4;
		}
		return 0;
	}
}
