package compreter.parsertree;

public class IfStatement extends Tree {
	Tree condition = null, 
			truePart = null, 
			falsePart = null;
	
	public IfStatement(Tree condition, Tree truePart){
		this.condition = condition;
		this.truePart = truePart;
	}
	
	public IfStatement(Tree condition, Tree truePart, Tree falsePart){
		this.condition = condition;
		this.truePart = truePart;
		this.falsePart = falsePart;
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
	
	public int tLineCount(){
		int tline = condition.tLineCount() + truePart.tLineCount() + 2;
		
		if(falsePart != null)
			tline += falsePart.tLineCount() + 1;
		return tline;
	}
}
