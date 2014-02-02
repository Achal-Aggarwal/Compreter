package compreter.parsertree;

import java.util.ArrayList;
import java.util.ListIterator;

public class WhileStatement extends Tree {
	Tree condition = null, body = null;
	public int startLine=-1,endLine=-1;
	public WhileStatement(Tree condition, Tree body){
		this.condition = condition;
		this.body = body;
		
		ArrayList<Tree> loopControlStatements = findLoopControlStatements();
		
		for (ListIterator<Tree> iter = loopControlStatements.listIterator(); iter.hasNext(); ) {
            LoopControlStatement s = (LoopControlStatement) iter.next();
            s.setLoop(this);
        }
	}
	
	public String toString(){
		return "while(" + condition.toString() + "){" + body.toString() + "}";
	}
	
	public String getCode(){
		String str =  condition.getCode() + 
				this.printLineNumber(true) + 
				"if " + condition.place + " == false " +
				"goto := " + String.valueOf(this.currentLineNumber + body.tLineCount() + 2) + "\n";
		
		startLine = this.currentLineNumber - condition.tLineCount();
		endLine = this.currentLineNumber + body.tLineCount() + 2;
			str += body.getCode() +
				this.printLineNumber(true) + 
				"goto := " + startLine +"\n";
		

		return str;
	}
	
	public int tLineCount(){
		return body.tLineCount() + 2;
	}
	
	public ArrayList<Tree> findLoopControlStatements(){
		ArrayList<Tree> statements = new ArrayList<Tree>();
		
		if(body instanceof LoopControlStatement){
			statements.add(body);
			return statements;
		}
		
		if(body instanceof WhileStatement)
			return statements;
		
		if(body instanceof Statements)
			statements.addAll(((Statements)body).findLoopControlStatements());
		
		return statements;
	}
}
