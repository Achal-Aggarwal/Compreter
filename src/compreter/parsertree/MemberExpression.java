package compreter.parsertree;

public class MemberExpression extends Tree {
	Tree primaryExpression = null;
	Tree expression = null;
	
	public MemberExpression(Tree pe, Tree e){
		this.primaryExpression = pe;
		this.expression = e;
		this.place = Tree.getNextTemp();
	}
	
	public String toString(){
		return primaryExpression.toString() + "[" + expression.toString() + "]"; 
	}
	
	public String getCode(){
		return expression.getCode() + 
				this.printLineNumber(true) + 
				this.place + " := " +
				primaryExpression.place + 
				" [ " + expression.place + " ]\n";
	}
	
	public int tLineCount(){
		return expression.tLineCount() + 1;
	}
}
