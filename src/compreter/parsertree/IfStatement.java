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
		String str =  "if (" + condition.toString() + ") {\n\t" + truePart.toString() + "}\n";
		if(falsePart != null)
			str+="else {\n\t" + falsePart.toString() + "}\n";
		
		return str;
	}
}
