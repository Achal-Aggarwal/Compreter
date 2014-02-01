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
	
	public String getCode(){
		int paramPullCount = paramlist!=null ? paramlist.tLineCount() : 0;
		
		String str = this.printLineNumber(true) + 
				"goto (" + String.valueOf(this.currentLineNumber + paramPullCount + compoundStatement.tLineCount()+3) +")\n";
		
		if(paramlist != null)
			str += paramlist.getCode();
		
		str += compoundStatement.getCode();
		
		String returnAddress = Tree.getNextTemp();
		str += this.printLineNumber(true) + returnAddress + " := pull\n";
		str += this.printLineNumber(true) + "goto ("+ returnAddress +")\n";
		
		return str;
	}
}
