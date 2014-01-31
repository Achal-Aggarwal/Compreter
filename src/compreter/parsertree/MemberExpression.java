package compreter.parsertree;

public class MemberExpression extends Tree {
	Tree primaryExpression = null;
	Tree expression = null;
	
	public MemberExpression(Tree pe, Tree e){
		this.primaryExpression = pe;
		this.expression = e;
	}
	
	public String toString(){
		return primaryExpression.toString() + "[ " + expression.toString() + " ]"; 
	}
}
