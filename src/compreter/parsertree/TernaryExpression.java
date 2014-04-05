package compreter.parsertree;

public class TernaryExpression extends Tree {
	Tree condition = null, 
			truePart = null, 
			falsePart = null;
	String labelTrue = null, labelFalse = null;
	public TernaryExpression(Tree condition, Tree truePart, Tree falsePart){
		this.condition = condition;
		this.truePart = truePart;
		this.falsePart = falsePart;
		this.place = Tree.getNextTemp();
		this.labelTrue = Tree.getNextLabel();
		this.labelFalse = Tree.getNextLabel();
	}
	
	public String toString(){
		return condition.toString() + "?" + truePart.toString() + ":" + falsePart.toString();
	}
	
	public String getCode(){
		String str =  condition.getCode() + 
				this.printLineNumber(true) + 
				"if " + condition.place + " == false " +
				"goto := " + String.valueOf(this.currentLineNumber + truePart.tLineCount() + 3) + "\n";
		
		String trueCode = truePart.getCode();
		
			str += (trueCode == "" ? "" : trueCode ) + 
				this.printLineNumber(true) +
				this.place + " := " + truePart.place + "\n" + 
				this.printLineNumber(true) + 
				"goto := " + String.valueOf(this.currentLineNumber + falsePart.tLineCount() + 2) +"\n";
		
		String falseCode = falsePart.getCode();
			str += (falseCode == "" ? "" : falseCode) +  
				this.printLineNumber(true) +
				this.place + " := " + falsePart.place + "\n";
		
		return str;
	}
	
	public String getLabelCode(){
		String str =  condition.getLabelCode() + 
				this.printLineNumber(true) + 
				"goto := " + this.labelFalse + 
				" if " + condition.place + " == false \n";
				
		
		String trueCode = truePart.getCode();
		
			str += (trueCode == "" ? "" : trueCode ) + 
				this.printLineNumber(true) +
				this.place + " := " + truePart.place + "\n" + 
				this.printLineNumber(true) + 
				"goto := " + this.labelTrue +"\n" + 
				this.printLineNumber(true) + 
				"label := " + this.labelFalse;

		String falseCode = falsePart.getCode();
			str += (falseCode == "" ? "" : falseCode) +  
				this.printLineNumber(true) +
				this.place + " := " + falsePart.place + "\n" +
				this.printLineNumber(true) +
				"label := " + this.labelTrue + "\n";
		
		return str;
	}
	
	public String getSimpleCode(){
		String str =  condition.getSimpleCode() + 
				this.printLineNumber(true) + 
				"goto := " + this.labelFalse + 
				" if " + condition.place + " == false \n";
				
		
		String trueCode = truePart.getCode();
		
			str += (trueCode == "" ? "" : trueCode ) + 
				this.printLineNumber(true) +
				this.place + " := " + truePart.place + "\n" + 
				this.printLineNumber(true) + 
				"goto := " + this.labelTrue +"\n" + 
				this.printLineNumber(true) + 
				"label := " + this.labelFalse;

		String falseCode = falsePart.getCode();
			str += (falseCode == "" ? "" : falseCode) +  
				this.printLineNumber(true) +
				this.place + " := " + falsePart.place + "\n" +
				this.printLineNumber(true) +
				"label := " + this.labelTrue + "\n";
		
		return str;
	}
	
	public int tLineCount(){
		return condition.tLineCount() + truePart.tLineCount() + falsePart.tLineCount() + 5;
	}
}
