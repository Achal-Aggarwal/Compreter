package compreter.parsertree;

public class AssignmentExpression extends Tree {
	Tree expression = null,
			identifer = null;
	
	
	public AssignmentExpression(Tree i, Tree e){
		this.identifer = i;
		this.expression = e;
		this.place = i.place;
	}
	
	public String toString(){
		return identifer.toString() + "=" + expression.toString();
	}
	
	public String getCode(){
		return expression.getCode() + this.printLineNumber(true) + identifer.place + " := " + expression.place + "\n";
	}
	
	public int tLineCount(){
		return this.expression.tLineCount() + 1;
	}
}
