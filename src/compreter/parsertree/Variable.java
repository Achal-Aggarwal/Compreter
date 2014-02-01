package compreter.parsertree;

import compreter.Symbol;

public class Variable extends Tree {
	Symbol identifier = null;
	Tree expression = null;
	
	public Variable(Symbol identifier){
		this.identifier= identifier;
	}
	
	public Variable(Symbol identifier, Tree expression){
		this.identifier= identifier;
		this.expression = expression;
	}
	
	public String toString(){
		String str = identifier.getValue();
		if(expression != null)
			str += "=" + expression.toString();
		
		return str;
	}
}
