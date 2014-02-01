package compreter.parsertree;

public class BracedExpression extends Tree {
	Tree expression = null;
	public BracedExpression(Tree expression){
		this.expression = expression;
		this.place = expression.place;
	}
	
	public String toString(){
		return "(" + expression + ")";
	}
	
	public String getCode(){
		return expression.getCode();
	}
	public int tLineCount(){
		return expression.tLineCount();
	}
}
