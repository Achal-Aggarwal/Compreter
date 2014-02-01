package compreter.parsertree;

public class BracedExpression extends Tree {
	Tree expression = null;
	public BracedExpression(Tree expression){
		this.expression = expression;
	}
	
	public String toString(){
		return "(" + expression + ")";
	}
}
