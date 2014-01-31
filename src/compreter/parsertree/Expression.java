package compreter.parsertree;

public class Expression extends Tree {
	Tree assignmentExpression = null;
	Tree conditionalExpression = null;
	
	
	public Expression(Tree ce, Tree ae){
		this.conditionalExpression = ce;
		this.assignmentExpression = ae;
	}
	
	public String toString(){
		return conditionalExpression.toString() + " = (" + assignmentExpression.toString() + ")";
	}
}
