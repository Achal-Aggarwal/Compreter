package compreter.parsertree;

public class ReturnStatement extends Tree {
	Tree expression = null;
	
	public ReturnStatement(){
	}
	
	public ReturnStatement(Tree exp){
		this.expression = exp;
	}
	
	public String toString(){
		String str = "return";
		
		if(expression != null)
			str += " " + expression.toString() + "";
		
		return str;
	}
	
	public String getCode(){
		String str = expression.getCode();
		
		String tempName = Tree.getNextTemp();
		
		str += this.printLineNumber(true) + 
			tempName + " := pop\n" + 
			this.printLineNumber(true) + 
			"push := " + expression.place + "\n" +  
			this.printLineNumber(true) + 
			"goto := " + tempName + "\n";
		
		return str;
	}
	
	public int tLineCount(){
		return expression.tLineCount() + 3;
	}
}
