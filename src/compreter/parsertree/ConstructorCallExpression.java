package compreter.parsertree;

import compreter.Symbol;

public class ConstructorCallExpression extends Tree {
	Tree argumentListOpt = null;
	Symbol identifier = null;
	
	public ConstructorCallExpression(Symbol symbol){
		this.identifier = symbol;
	}
	
	public ConstructorCallExpression(Symbol symbol, Tree argumentListOpt){
		this.identifier = symbol;
		this.argumentListOpt = argumentListOpt;
	}
	
	public String toString(){
		String str = identifier.getValue() + "(";
		if(argumentListOpt!=null)
			str += argumentListOpt.toString();
		str += ")";
		
		return str;
	}
}
