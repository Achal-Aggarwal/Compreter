package compreter.parsertree;

import compreter.Symbol;

public class Variable extends Tree {
	Identifier identifier = null;
	Tree expression = null;
	
	public Variable(Symbol i){
		this.identifier = new Identifier(i.getValue(),-1,current_block);
		it.addIdentifier(identifier);
	}
	
	public Variable(Symbol i, Tree expression){
		this.identifier = new Identifier(i.getValue(),-1,current_block);
		it.addIdentifier(identifier);
		this.expression = expression;
	}
	
	public String toString(){
		String str = identifier.getNewName(false);
		if(expression != null)
			str += "=" + expression.toString();
		
		return str;
	}
	
	public String getCode(){
		String str = "";
		if(expression != null){
			str += expression.getCode() +
				this.printLineNumber(true) + 
				identifier.getNewName() + " := " + expression.place + "\n";
		}
		
		return str;
	}
	
	public String getSimpleCode(){
		String str = "";
		if(expression != null){
			str += expression.getSimpleCode() +
				this.printLineNumber(true) + 
				identifier.getNewName() + " := " + expression.place + "\n";
		}
		
		return str;
	}
	
	public int tLineCount(){
		return expression.tLineCount() + 1;
	}
}
