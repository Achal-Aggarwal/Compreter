package compreter.parsertree;

public class WhileStatement extends Tree {
	Tree condition = null, body = null;
	
	public WhileStatement(Tree condition, Tree body){
		this.condition = condition;
		this.body = body;
	}
	
	public String toString(){
		return "while(" + condition.toString() + "){" + body.toString() + "}";
	}
}
