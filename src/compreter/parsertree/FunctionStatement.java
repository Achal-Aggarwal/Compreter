package compreter.parsertree;

import compreter.Symbol;

public class FunctionStatement extends Tree{
	Symbol name = null;
	Tree paramlist = null;
	Tree compoundStatement = null;
	
	public FunctionStatement(Symbol name, Tree paramList, Tree compoundStatement){
		this.name = name;
		this.paramlist = paramList;
		this.compoundStatement = compoundStatement;
	}
	
	public String toString(){
		String str = "function " + name.getValue() + "(";
		if(paramlist != null)
			str += paramlist.toString();
		str += "){" + compoundStatement.toString() + "}";
		
		return str;
	}
}
