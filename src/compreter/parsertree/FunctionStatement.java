package compreter.parsertree;

import compreter.Symbol;

public class FunctionStatement extends Tree{
	Identifier name = null;
	Tree paramlist = null;
	Tree compoundStatement = null;
	String lableLast = null;
	String returnAddress = null;
	
	public FunctionStatement(Symbol name, Tree paramList, Tree compoundStatement){
		this.name = new Identifier(name.getValue(),-1,current_block + " : func");
		it.addIdentifier(this.name);
		this.paramlist = paramList;
		this.compoundStatement = compoundStatement;
		this.lableLast = Tree.getNextLabel();
		this.returnAddress = Tree.getNextTemp();
		
		if (compoundStatement instanceof Statements)
			((Statements)compoundStatement).setReturnAddresses(this.returnAddress);
		
		if (compoundStatement instanceof ReturnStatement)
			((ReturnStatement)compoundStatement).setReturnAddress(this.returnAddress);
	}
	
	public void setReturnStatements(){
		
	}
	
	public String toString(){
		String str = "function " + name.getNewName(false) + "(";
		if(paramlist != null)
			str += paramlist.toString();
		
		str += "){";
		
		Object[] ids = it.getAllIdentifier();
		
		if(ids.length > 0){
			str += "var ";
			for(Object id:ids){
				str += ((String) id) + ",";
			}
			
			str = str.substring(0,str.length()-1) + ";";
		}
			
		
		str +=  compoundStatement.toString() + "}";
		
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
	
	public String getLabelCode(){
		String str = this.printLineNumber(true) + "goto := " + this.lableLast +"\n";
		
		str += this.printLineNumber(true) + "label := " + name.getNewName() + "\n";

		str += this.printLineNumber(true) + this.returnAddress + " := pop\n";
		if(paramlist != null)
			str += paramlist.getLabelCode();
		
		str += compoundStatement.getLabelCode();

		str += this.printLineNumber(true) + "goto := "+ returnAddress +"\n";
		str += this.printLineNumber(true) + "label := "+ this.lableLast +"\n";
		
		return str;
	}
	
	public String getSimpleCode(){
		String str= "";
		
		str += this.printLineNumber(true) + "function := " + name.getNewName();

		str += " (" + paramlist.getSimpleCode() + ")\n";
		
		str += compoundStatement.getSimpleCode();

		str += this.printLineNumber(true) + "return := function "+ name.getNewName() +"\n";
		
		return str;
	}
}
