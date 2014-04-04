package compreter.parsertree;

public class ReturnStatement extends Tree {
	Tree expression = null;
	String returnAddress = null;
	public ReturnStatement(){
	}
	
	public ReturnStatement(Tree exp){
		this.expression = exp;
	}
	
	public void setReturnAddress(String address){
		this.returnAddress = address;
	}
	
	public String toString(){
		String str = "return";
		
		if(expression != null)
			str += " " + expression.toString() + "";
		
		return str;
	}
	
	public String getCode(){
		String str = expression.getCode();
		
		str += this.printLineNumber(true) + 
			"push := " + expression.place + "\n" +  
			this.printLineNumber(true) + 
			"goto := " + this.returnAddress + "\n";
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = expression.getCode();
		
		str += this.printLineNumber(true) + 
			"return := " + expression.place + "\n";
		
		return str;
	}
	
	public int tLineCount(){
		return expression.tLineCount() + 3;
	}
}
