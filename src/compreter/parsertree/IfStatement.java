package compreter.parsertree;

public class IfStatement extends Tree {
	Tree condition = null, 
			truePart = null, 
			falsePart = null;
	String labelTrue = null, labelFalse = null;

	public IfStatement(Tree condition, Tree truePart){
		this.condition = condition;
		this.truePart = truePart;
		this.labelTrue = Tree.getNextLabel();
	}
	
	public IfStatement(Tree condition, Tree truePart, Tree falsePart){
		this.condition = condition;
		this.truePart = truePart;
		this.falsePart = falsePart;
		this.labelTrue = Tree.getNextLabel();
		this.labelFalse = Tree.getNextLabel();
	}
	
	public String toString(){
		String str =  "if(" + condition.toString() + "){" + truePart.toString() + "}";
		if(falsePart != null)
			str+="else{" + falsePart.toString() + "}";
		
		return str;
	}
	
	public String getCode(){
		String str =  condition.getCode() + 
				this.printLineNumber(true) + 
				"if " + condition.place + " == false " +
				"goto := " + String.valueOf(this.currentLineNumber + truePart.tLineCount() + (falsePart != null ? 2 : 1)) + "\n" +
				truePart.getCode();
		if(falsePart != null)
			str +=	this.printLineNumber(true) + 
				"goto := " + String.valueOf(this.currentLineNumber + falsePart.tLineCount() + 1) +"\n" +
				falsePart.getCode();
		
		return str;
	}
	
	public String getLabelCode(){
		if(falsePart == null){
			return condition.getLabelCode() + 
					this.printLineNumber(true) + 
					"goto := " + this.labelFalse +
					" if " + condition.place + " == false \n" + 
					truePart.getLabelCode() + 
					this.printLineNumber(true) +
					"label := " + this.labelFalse;
		}
		else{
			return condition.getLabelCode() + 
					this.printLineNumber(true) + 
					"goto := " + this.labelFalse +
					" if " + condition.place + " == false \n" + 
					truePart.getLabelCode() + 
					this.printLineNumber(true) +
					"goto := " + this.labelTrue + "\n" +
					this.printLineNumber(true) +
					"label := " + this.labelFalse + "\n" + 
					falsePart.getLabelCode() +
					this.printLineNumber(true) +
					"label := " + this.labelTrue + "\n";
		}
	}
	
	public String getSimpleCode(){
		if(falsePart == null){
			return condition.getSimpleCode() + 
					this.printLineNumber(true) + 
					"goto := " + this.labelFalse +
					" if " + condition.place + " == false \n" + 
					truePart.getSimpleCode() + 
					this.printLineNumber(true) +
					"label := " + this.labelFalse;
		}
		else{
			return condition.getSimpleCode() + 
					this.printLineNumber(true) + 
					"goto := " + this.labelFalse +
					" if " + condition.place + " == false \n" + 
					truePart.getSimpleCode() + 
					this.printLineNumber(true) +
					"goto := " + this.labelTrue + "\n" +
					this.printLineNumber(true) +
					"label := " + this.labelFalse + "\n" + 
					falsePart.getSimpleCode() +
					this.printLineNumber(true) +
					"label := " + this.labelTrue + "\n";
		}
	}
	
	public int tLineCount(){
		int tline = condition.tLineCount() + truePart.tLineCount() + 2;
		
		if(falsePart != null)
			tline += falsePart.tLineCount() + 1;
		return tline;
	}
}
