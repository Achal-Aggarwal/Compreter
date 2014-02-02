package compreter.parsertree;

import compreter.Symbol;

public class FunctionStatement extends Tree{
	Identifier name = null;
	Tree paramlist = null;
	Tree compoundStatement = null;
	
	public FunctionStatement(Symbol name, Tree paramList, Tree compoundStatement){
		this.name = new Identifier(name.getValue());
		it.addIdentifier(this.name);
		this.paramlist = paramList;
		this.compoundStatement = compoundStatement;
	}
	
	public String toString(){
		String str = "function " + name.getNewName() + "(";
		if(paramlist != null)
			str += paramlist.toString();
		str += "){" + compoundStatement.toString() + "}";
		
		return str;
	}
	
	public String getCode(){
		int paramPullCount = paramlist!=null ? paramlist.tLineCount() : 0;
		
		String str = this.printLineNumber(true) +
				"goto := " + String.valueOf(this.currentLineNumber + paramPullCount + compoundStatement.tLineCount()+4) +"\n";
		
		str += this.printLineNumber(true) + "label := " + name.getNewName() + "\n";
				
		if(paramlist != null)
			str += paramlist.getCode();
		
		str += compoundStatement.getCode();
		
		String returnAddress = Tree.getNextTemp();
		str += this.printLineNumber(true) + returnAddress + " := pull\n";
		str += this.printLineNumber(true) + "goto := "+ returnAddress +"\n";
		
		return str;
	}
}
