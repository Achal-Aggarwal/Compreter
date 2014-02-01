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
}
