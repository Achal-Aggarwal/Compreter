package compreter.parsertree;

public class Expression extends Tree {
	Tree assignmentExpression = null;
	Tree conditionalExpression = null;
	
	public Expression(Tree ce){
		this.conditionalExpression = ce;
	}
	
	public Expression(Tree ce, Tree ae){
		this.conditionalExpression = ce;
		this.assignmentExpression = ae;
	}
}
