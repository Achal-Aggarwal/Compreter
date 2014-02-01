package compreter.parsertree;

public class TernaryExpression extends Tree {
	Tree condition = null, 
			truePart = null, 
			falsePart = null;
	
	public TernaryExpression(Tree condition, Tree truePart, Tree falsePart){
		this.condition = condition;
		this.truePart = truePart;
		this.falsePart = falsePart;
	}
	
	public String toString(){
		return condition.toString() + "?" + truePart.toString() + ":" + falsePart.toString();
	}
}
